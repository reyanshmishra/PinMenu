package com.samsung.gallery.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the popup class which will display the images from particular
 * folder or say bucket.
 * But the UX is not impressive of this class which can be improved
 * How the UX is not good? Now suppose you have more then 500 images in bucket
 * and you want to change them as your scroll on screen I don't think it would look good.
 * Think about it.
 */

public class PinDialog extends Dialog {


    private int mStatusBarHeight;
    private int mDegreeSteps;
    private int mScreenWidth;
    private int mRadius;
    private int mAngle;

    boolean mActionDown = true;
    boolean mDoneAnimating = false;

    private GradientDrawable mGradientDrawable;
    private PinHolder mPinHolder;
    private PinMenu mZoomedView;
    private PinSelectListener mPinSelectListener;


    public PinDialog(@NonNull Context context) {
        super(context, android.R.style.Theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_popup);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mPinHolder = (PinHolder) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setShape(GradientDrawable.OVAL);
        mGradientDrawable.setStroke(8, Color.parseColor("#90000000"));

        mStatusBarHeight = ReyUtils.getStatusBarHeight(mPinHolder.getContext());
        mScreenWidth = ReyUtils.getScreenWidth();
        mDegreeSteps = mScreenWidth / 180;
        mRadius = mPinHolder.getMenuRadius();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setPinSelectListener(PinSelectListener pinSelectListener) {
        mPinSelectListener = pinSelectListener;
    }

    public boolean passTouchEvent(MotionEvent event, View view) {
        if (mActionDown) {
            mActionDown = false;
            mPinHolder.drawOverlay(event.getRawX(), event.getRawY(), view);
            setIndicator(event.getRawX(), event.getRawY());
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (mPinSelectListener != null && mZoomedView != null) {
                    mPinSelectListener.pinSelected(mZoomedView);
                }
                mActionDown = true;
                mZoomedView = null;
                mDoneAnimating = false;
                dismiss();
                break;
            case MotionEvent.ACTION_MOVE:
                highLightMenu(event);
                break;
        }
        return false;
    }


    private void highLightMenu(MotionEvent event) {
        float centerX = event.getRawX();
        float centerY = event.getRawY() - mStatusBarHeight;

        float[] a = new float[2];

        for (int i = 0; i < mPinHolder.getChildCount(); i++) {
            View view = mPinHolder.getChildAt(i);

            if (view.getId() == R.id.pin_name_id) continue;

            a[0] = view.getX();
            a[1] = view.getY();

            float delMaxX = a[0] + view.getWidth();
            float delMaxY = a[1] + view.getHeight();


            if (centerX > a[0] && centerX < delMaxX && centerY > a[1] && centerY < delMaxY) {
                if (mDoneAnimating) {
                    if (mZoomedView == null) {
                        mZoomedView = (PinMenu) view;
                        mPinHolder.setPinName(mZoomedView.getPinName());
                        ReyUtils.zoomInView(mZoomedView);
                        mZoomedView.selected();
                    }
                }
            } else {
                if (mZoomedView != null && mZoomedView == view) {
                    ReyUtils.zoomOutView(mZoomedView);
                    mPinHolder.setPinName("");
                    mZoomedView = null;
                }
                ((PinMenu) view).unSelected();
            }
        }

    }


    public void setIndicator(float x, float y) {

        if (y < mRadius + ReyUtils.getStatusBarHeight(mPinHolder.getContext())) {
            mAngle = (int) (((x / 2) / mDegreeSteps) + 0);
        } else {
            mAngle = (int) ((x / mDegreeSteps) + 70) * -(1);
        }


        int child = mPinHolder.getChildCount();
        List<Animator> animList = new ArrayList<>();

        for (int i = 0; i < child; i++) {
            View view = mPinHolder.getChildAt(i);
            if (view.getId() == R.id.pin_name_id) {
                continue;
            }

            int xP = ReyUtils.getNewX(mRadius, x, mAngle);
            int yP = ReyUtils.getNewY(mRadius, y - mStatusBarHeight, mAngle);

            view.setX(xP - view.getWidth() / 2);
            view.setY(yP - view.getHeight() / 2);

            mAngle += mPinHolder.getAngle();
            animList.add(createShowItemAnimator(x, y, view));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mDoneAnimating = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    private Animator createShowItemAnimator(float x, float y, View item) {

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.translationX(x, item.getX()),
                AnimatorUtils.translationY(y, item.getY())
        );

        return anim;
    }

    RecyclerView.OnItemTouchListener mOnItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            if (isShowing()) {
                return true;
            }
            gestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            passTouchEvent(e, child);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    };

    final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            show();
        }
    });

    public void addPinListener(View view) {
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.addOnItemTouchListener(mOnItemTouchListener);
        } else {
            view.setOnLongClickListener(v -> {
                show();
                return true;
            });
        }
    }
}
