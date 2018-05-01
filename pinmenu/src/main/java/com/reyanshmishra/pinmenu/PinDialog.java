/*******************************************************************************
 * Copyright 2017-2018 Reyansh Mishra
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/


package com.reyanshmishra.pinmenu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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

    private final String TAG = PinDialog.class.getName();

    private int mStatusBarHeight;
    private int mDegreeSteps;
    private int mScreenWidth;
    private int mRadius;
    private int mAngle;

    boolean mActionDown = true;
    boolean mDoneAnimating = false;

    private PinMenuHolder mPinMenuHolder;
    private PinMenu mZoomedView;
    private PinSelectListener mPinSelectListener;

    public PinDialog(@NonNull Context context) {
        super(context, android.R.style.Theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mStatusBarHeight = ReyUtils.getStatusBarHeight(context);
        mScreenWidth = ReyUtils.getScreenWidth();
        mDegreeSteps = mScreenWidth / 180;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup view = this.findViewById(android.R.id.content);
        View child = view.getChildAt(0);
        if (!(child instanceof PinMenuHolder)) {
            throw new ClassCastException("Root view has to be PinHolder");
        } else {
            mPinMenuHolder = (PinMenuHolder) child;
        }
        mRadius = mPinMenuHolder.getMenuRadius();
    }

    public void setPinSelectListener(PinSelectListener pinSelectListener) {
        mPinSelectListener = pinSelectListener;
    }

    public boolean passTouchEvent(MotionEvent event, View view) {
        if (mActionDown) {
            mActionDown = false;
            mPinMenuHolder.drawOverlay(event.getRawX(), event.getRawY(), view);
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

        for (int i = 0; i < mPinMenuHolder.getChildCount(); i++) {
            View view = mPinMenuHolder.getChildAt(i);

            if (!(view instanceof PinMenu)) {
                throw new ClassCastException("View:-" + view.getId() + " Not instance of PinMenu");
            }


            a[0] = view.getX();
            a[1] = view.getY();

            float delMaxX = a[0] + view.getWidth();
            float delMaxY = a[1] + view.getHeight();


            if (centerX > a[0] && centerX < delMaxX && centerY > a[1] && centerY < delMaxY) {
                if (mDoneAnimating) {
                    if (mZoomedView == null) {
                        mZoomedView = (PinMenu) view;
                        mPinMenuHolder.setPinName(mZoomedView.getPinName());
                        ReyUtils.zoomInView(mZoomedView);
                        mZoomedView.selected();
                    }
                }
            } else {
                if (mZoomedView != null && mZoomedView == view) {
                    ReyUtils.zoomOutView(mZoomedView);
                    mPinMenuHolder.setPinName("");
                    mZoomedView = null;
                }
                ((PinMenu) view).unSelected();
            }
        }

    }


    public void setIndicator(float x, float y) {

        if (y < mRadius + ReyUtils.getStatusBarHeight(mPinMenuHolder.getContext())) {
            mAngle = (int) (((x / 2) / mDegreeSteps) + 0);
        } else {
            mAngle = (int) ((x / mDegreeSteps) + 70) * -(1);
        }


        int child = mPinMenuHolder.getChildCount();
        List<Animator> animList = new ArrayList<>();

        for (int i = 0; i < child; i++) {
            View view = mPinMenuHolder.getChildAt(i);

            int xP = ReyUtils.getNewX(mRadius, x, mAngle);
            int yP = ReyUtils.getNewY(mRadius, y - mStatusBarHeight, mAngle);

            view.setX(xP - view.getWidth() / 2);
            view.setY(yP - view.getHeight() / 2);

            mAngle += mPinMenuHolder.getAngle();
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
            mGestureDetector.onTouchEvent(e);
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

    final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public void onLongPress(MotionEvent e) {
            show();
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }
    });

    public void addToRecyclerView(RecyclerView view) {
        if (view instanceof RecyclerView) {
//            RecyclerView recyclerView = (RecyclerView) view;
            view.addOnItemTouchListener(mOnItemTouchListener);
        } else {

        }
    }
}
