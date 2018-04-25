package com.samsung.gallery.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

public class PinMenu extends android.support.v7.widget.AppCompatImageView {

    GradientDrawable mBackgroundDrawable;
    int mBackgroundColor, mSelectedBackgroundColor;

    public String getPinName() {
        return mPinName;
    }

    private String mPinName;

    public PinMenu(Context context) {
        super(context);
        init();
    }

    public PinMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public PinMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PinMenu, defStyleAttr, 0);

        if (a.hasValue(R.styleable.PinMenu_pin_name)) {
            mPinName = a.getString(R.styleable.PinMenu_pin_name);
        }
        if (a.hasValue(R.styleable.PinMenu_pin_background_color)) {
            mBackgroundColor = a.getColor(R.styleable.PinMenu_pin_background_color, Color.WHITE);

        }
        if (a.hasValue(R.styleable.PinMenu_pin_selected_color)) {
            mSelectedBackgroundColor = a.getColor(R.styleable.PinMenu_pin_selected_color, Color.WHITE);
        }
        a.recycle();
        init();
    }

    private void init() {
        mBackgroundDrawable = new GradientDrawable();
        mBackgroundDrawable.setShape(GradientDrawable.OVAL);
        mBackgroundDrawable.setColor(mBackgroundColor);
        setBackground(mBackgroundDrawable);
    }

    public void selected() {
        mBackgroundDrawable.setColor(mSelectedBackgroundColor);
        setBackground(mBackgroundDrawable);
        setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void unSelected() {
        mBackgroundDrawable.setColor(mBackgroundColor);
        setBackground(mBackgroundDrawable);
        setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);
    }
}



