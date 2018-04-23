package com.reyanshmishra.pin;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    boolean mActionDown = true;

    public static String TAG = "ImageViewerDialog";
    private GradientDrawable mGradientDrawable;
    private PinHolder mViewGroup;
    private ImageView mIndicator;
    private TextView mPinName;

    private int mStatusBarHeight;
    private int mDegreeSteps;
    private int mWidth;
    private int mRadius = 250;
    private int mDegree;
    private PinMenu mZoomedView;
    boolean mDoneAnimating = false;
    private Context mContext;
    private PinMenuSelectListener mPinMenuSelectListener;

    public PinDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Black_NoTitleBar);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.dialog_popup);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mViewGroup = (PinHolder) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        mContext = context;
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setShape(GradientDrawable.OVAL);
        mGradientDrawable.setStroke(8, Color.parseColor("#90000000"));

        mIndicator = new ImageView(context);
        mIndicator.setId(R.id.image);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ReyUtils.dpToPx(context, 48), ReyUtils.dpToPx(context, 48));

        mIndicator.setLayoutParams(layoutParams);
        mIndicator.setImageDrawable(mGradientDrawable);

        mPinName = new TextView(context);
        RelativeLayout.LayoutParams paramsText = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPinName.setLayoutParams(paramsText);
        mPinName.setTextSize(48);
        mPinName.setTypeface(Typeface.DEFAULT_BOLD);
        mPinName.setId(235);
        mViewGroup.addView(mPinName);
        mViewGroup.addView(mIndicator);
        mPinName.setVisibility(View.GONE);

        mStatusBarHeight = ReyUtils.getStatusBarHeight(mViewGroup.getContext());
        mWidth = ReyUtils.getScreenWidth();
        mDegreeSteps = mWidth / 180;
    }


    public void setPinMenuSelectListener(PinMenuSelectListener pinMenuSelectListener) {
        mPinMenuSelectListener = pinMenuSelectListener;
    }

    public boolean passTouchEvent(MotionEvent event, View view) {
        if (mActionDown) {
            mActionDown = false;
            mViewGroup.drawOverlay(view);
            updateIndicator(event.getRawX(), event.getRawY());
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (mPinMenuSelectListener != null && mZoomedView != null) {
                    mPinMenuSelectListener.pinMenuSelected(mZoomedView.getId(), mZoomedView);
                }
                mActionDown = true;
                mZoomedView = null;
                mDoneAnimating = false;
                dismiss();
                break;
            case MotionEvent.ACTION_MOVE:
                fillColor(event);
                break;
        }
        return false;
    }


    private void fillColor(MotionEvent event) {
        float centerX = event.getRawX();
        float centerY = event.getRawY() - mStatusBarHeight;

        float[] a = new float[2];

        for (int i = 0; i < mViewGroup.getChildCount(); i++) {
            View view = mViewGroup.getChildAt(i);

            if (view.getId() == R.id.image || view.getId() == 235) continue;

            a[0] = view.getX();
            a[1] = view.getY();

            float delMaxX = a[0] + view.getWidth();
            float delMaxY = a[1] + view.getHeight();


            if (centerX > a[0] && centerX < delMaxX && centerY > a[1] && centerY < delMaxY) {
                if (mDoneAnimating) {
                    if (mZoomedView == null) {
                        mZoomedView = (PinMenu) view;
                        mPinName.setText(mZoomedView.getPinName());
                        mPinName.setVisibility(View.VISIBLE);
                        ((ImageView) view).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                        ReyUtils.zoomInView(mZoomedView);
                    }
                }
            } else {
                if (mZoomedView != null && mZoomedView == view) {
                    ReyUtils.zoomOutView(mZoomedView);
                    mPinName.setVisibility(View.INVISIBLE);
                    mZoomedView = null;
                }

                ((ImageView) view).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            }
        }

    }


    public void updateIndicator(float x, float y) {
        mIndicator.setX(x - mIndicator.getWidth() / 2);
        mIndicator.setY(y - (mIndicator.getHeight() / 2) - mStatusBarHeight);

        alignPinNameText(mIndicator.getX(), mIndicator.getY());

        if (y < mRadius + ReyUtils.getStatusBarHeight(mViewGroup.getContext())) {
            mDegree = (int) (((x / 2) / mDegreeSteps) + 0);
        } else {
            mDegree = (int) ((x / mDegreeSteps) + 70) * -(1);
        }


        int child = mViewGroup.getChildCount();
        List<Animator> animList = new ArrayList<>();

        for (int i = 0; i < child; i++) {
            View view = mViewGroup.getChildAt(i);
            if (view.getId() == R.id.image || view.getId() == 235) {
                continue;
            }
            float xI = mIndicator.getX();
            float yI = mIndicator.getY();

            int xP = ReyUtils.getNewX(mRadius, xI, mDegree);
            int yP = ReyUtils.getNewY(mRadius, yI, mDegree);

            view.setX(xP);
            view.setY(yP);

            mDegree += 40;
            animList.add(createShowItemAnimator(view));
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

    private void alignPinNameText(float x, float y) {
        int textPosition = (mRadius + mRadius);

        if (textPosition > y) {
            mPinName.setY(mIndicator.getY() + textPosition);
        } else {
            mPinName.setY(mIndicator.getY() - textPosition);
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mPinName.getLayoutParams();

        if (x < mWidth / 2) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        mPinName.setLayoutParams(params);

    }


    private Animator createShowItemAnimator(View item) {

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.translationX(mIndicator.getX(), item.getX()),
                AnimatorUtils.translationY(mIndicator.getY(), item.getY())
        );

        return anim;
    }

    public void addRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnItemTouchListener(mOnItemTouchListener);
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

    final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            show();
        }
    });
}
