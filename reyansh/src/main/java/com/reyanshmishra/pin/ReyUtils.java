package com.reyanshmishra.pin;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class ReyUtils {


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    public static int getNewX(int radius, float x, double degree) {
        double radian = degree * 3.14 / 180;
        return (int) (x + radius * Math.cos(radian));
    }


    public static int getNewY(int radius, float y, double degree) {
        double radian = degree * 3.14 / 180;
        return (int) (y + radius * Math.sin(radian));
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static Animator zoomOutView(View item) {

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.scaleX(1.2f, 1f),
                AnimatorUtils.scaleY(1.2f, 1f)
        );
        anim.setDuration(300);
        anim.start();
        return anim;
    }


    public static Animator zoomInView(View item) {

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.scaleX(1f, 1.2f),
                AnimatorUtils.scaleY(1f, 1.2f)
        );
        anim.setDuration(300);
        anim.start();
        return anim;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
