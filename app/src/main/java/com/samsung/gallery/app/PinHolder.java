package com.samsung.gallery.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PinHolder extends RelativeLayout {

    private Paint mOverlayPaint;

    private Path mPath;

    public PinHolder(Context context) {
        super(context);
        init();
    }

    public PinHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PinHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PinHolder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mOverlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOverlayPaint.setAntiAlias(true);
        mOverlayPaint.setColor(Color.parseColor("#80ffffff"));
        mPath = new Path();
    }

    public void drawOverlay(View view) {
        mPath.reset();
        mOverlayPaint.setAntiAlias(true);
        mPath.setFillType(Path.FillType.EVEN_ODD);

        mPath.moveTo(0, 0);
        mPath.lineTo(ReyUtils.getScreenWidth(), 0);
        mPath.lineTo(ReyUtils.getScreenWidth(), ReyUtils.getScreenHeight());
        mPath.lineTo(0, ReyUtils.getScreenHeight());
        mPath.close();
        if (view == null) {
            invalidate();
            return;
        }
        mPath.moveTo(view.getX(), view.getY());
        mPath.lineTo(view.getX() + view.getWidth(), view.getY());
        mPath.lineTo(view.getX() + view.getWidth(), view.getY() + view.getHeight());
        mPath.lineTo(view.getX(), view.getY() + view.getHeight());
        mPath.close();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawPath(mPath, mOverlayPaint);
        canvas.drawPath(mPath, mOverlayPaint);
    }

}

