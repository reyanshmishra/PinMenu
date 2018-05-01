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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class PinMenuHolder extends RelativeLayout {


    private static final int DEFAULT_OVERLAY_COLOR = Color.parseColor("#80ffffff");
    private static final int DEFAULT_TEXT_COLOR = Color.GRAY;
    private static final int DEFAULT_TEXT_SIZE = 80;

    private static final int DEFAULT_MENU_RADIUS = 250;
    private static final int DEFAULT_INDICATOR_RADIUS = 50;
    private static final int DEFAULT_INDICATOR_COLOR = Color.GRAY;
    private static final int DEFAULT_INDICATOR_WIDTH = 10;
    private static final int DEFAULT_MENU_ANGLE = 40;

    private Paint mOverlayPaint;
    private float mX, mY;
    private int mStatusBarHeight;
    private Path mPath;
    private String mPinName;
    private Paint mIndicatorPaint;
    private Paint mNamePaint;
    private int mScreenWidth;
    private int mOverlayColor = DEFAULT_OVERLAY_COLOR;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mTextSize = DEFAULT_TEXT_SIZE;


    private int mMenuRadius = DEFAULT_MENU_RADIUS;
    private int mIndicatorRadius = DEFAULT_INDICATOR_RADIUS;
    private int mIndicatorColor = DEFAULT_INDICATOR_COLOR;
    private int mIndicatorBorderWidth = DEFAULT_INDICATOR_WIDTH;
    private boolean mDrawOverView = true;
    private int mAngle = DEFAULT_MENU_ANGLE;


    public PinMenuHolder(Context context) {
        super(context);
        init();
    }

    public PinMenuHolder(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public PinMenuHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PinMenuHolder, defStyleAttr, 0);

        if (a.hasValue(R.styleable.PinMenuHolder_pin_holder_overlay_color)) {
            mOverlayColor = a.getColor(R.styleable.PinMenuHolder_pin_holder_overlay_color, 0);
        }
        if (a.hasValue(R.styleable.PinMenuHolder_pin_holder_text_color)) {
            mTextColor = a.getColor(R.styleable.PinMenuHolder_pin_holder_text_color, 0);
        }

        if (a.hasValue(R.styleable.PinMenuHolder_pin_holder_text_size)) {
            mTextSize = a.getDimensionPixelSize(R.styleable.PinMenuHolder_pin_holder_text_size, 35);
        }

        if (a.hasValue(R.styleable.PinMenuHolder_pin_holder_menu_radius)) {
            mMenuRadius = a.getDimensionPixelSize(R.styleable.PinMenuHolder_pin_holder_menu_radius, 0);
        }

        if (a.hasValue(R.styleable.PinMenuHolder_pin_holder_draw_over_view)) {
            mDrawOverView = a.getBoolean(R.styleable.PinMenuHolder_pin_holder_draw_over_view, true);
        }
        if (a.hasValue(R.styleable.PinMenuHolder_pin_holder_indicator_border_width)) {
            mIndicatorBorderWidth = a.getDimensionPixelSize(R.styleable.PinMenuHolder_pin_holder_indicator_border_width, ReyUtils.spToPx(5, getContext()));
        }

        if (a.hasValue(R.styleable.PinMenuHolder_pin_holder_indicator_color)) {
            mIndicatorColor = a.getColor(R.styleable.PinMenuHolder_pin_holder_indicator_color, Color.GRAY);
        }
        if (a.hasValue(R.styleable.PinMenuHolder_pin_holder_indicator_radius)) {
            mIndicatorRadius = a.getDimensionPixelSize(R.styleable.PinMenuHolder_pin_holder_indicator_radius, 18);
        }

        if (a.hasValue(R.styleable.PinMenuHolder_pin_holder_angle)) {
            mAngle = a.getInt(R.styleable.PinMenuHolder_pin_holder_angle, 35);
        }

        a.recycle();

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PinMenuHolder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPath = new Path();

        mOverlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOverlayPaint.setAntiAlias(true);
        mOverlayPaint.setColor(mOverlayColor);

        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndicatorPaint.setColor(mIndicatorColor);
        mIndicatorPaint.setStyle(Paint.Style.STROKE);
        mIndicatorPaint.setStrokeWidth(mIndicatorBorderWidth);


        mNamePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mNamePaint.setTypeface(Typeface.DEFAULT_BOLD);
        mNamePaint.setColor(mTextColor);
        mNamePaint.setTextSize(mTextSize);
        mNamePaint.setLinearText(true);
        mNamePaint.setTextAlign(Paint.Align.RIGHT);
        mStatusBarHeight = ReyUtils.getStatusBarHeight(getContext());
        mScreenWidth = ReyUtils.getScreenWidth();
    }

    public void drawOverlay(float x, float y, View view) {
        mX = x;
        mY = y - mStatusBarHeight;
        mPath.reset();
        mPath.setFillType(Path.FillType.EVEN_ODD);

        mPath.moveTo(0, 0);
        mPath.lineTo(ReyUtils.getScreenWidth(), 0);
        mPath.lineTo(ReyUtils.getScreenWidth(), ReyUtils.getScreenHeight());
        mPath.lineTo(0, ReyUtils.getScreenHeight());
        mPath.close();

        if (mDrawOverView && view != null) {
            mPath.moveTo(view.getX(), view.getY());
            mPath.lineTo(view.getX() + view.getWidth(), view.getY());
            mPath.lineTo(view.getX() + view.getWidth(), view.getY() + view.getHeight());
            mPath.lineTo(view.getX(), view.getY() + view.getHeight());
            mPath.close();
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawPath(mPath, mOverlayPaint);
        canvas.drawPath(mPath, mOverlayPaint);
        canvas.drawCircle(mX, mY, mIndicatorRadius, mIndicatorPaint);
        drawText(canvas);
    }

    public void setPinName(String pinName) {
        mPinName = pinName;
        invalidate();
    }


    private void drawText(Canvas canvas) {
        int textPosition = (mMenuRadius + mMenuRadius);
        float yPos;
        if (textPosition > mY) {
            yPos = mY + textPosition;
        } else {
            yPos = mY - textPosition;
        }
        Rect textBounds = new Rect();
        if (mPinName != null) {
            mNamePaint.getTextBounds(mPinName, 0, mPinName.length(), textBounds);
            if (mX > mScreenWidth / 2) {
                canvas.drawText(mPinName, textBounds.width(), yPos, mNamePaint);
            } else {
                canvas.drawText(mPinName, ReyUtils.getScreenWidth(), yPos, mNamePaint);
            }
        }
    }

    public int getMenuRadius() {
        return mMenuRadius;
    }

    public int getAngle() {
        return mAngle;
    }
}

