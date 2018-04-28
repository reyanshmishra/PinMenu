package com.samsung.gallery.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

public class PinMenu extends android.support.v7.widget.AppCompatImageView {

    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static final int DEFAULT_SELECTED_BACKGROUND_COLOR = Color.MAGENTA;
    private static final int DEFAULT_ICON_COLOR = Color.GRAY;
    private static final int DEFAULT_SELECTED_ICON_COLOR = Color.WHITE;


    private GradientDrawable mBackgroundDrawable;
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    private int mSelectedBackgroundColor = DEFAULT_SELECTED_BACKGROUND_COLOR;
    private int mIconColor = DEFAULT_ICON_COLOR;
    private int mSelectedIconColor = DEFAULT_SELECTED_ICON_COLOR;


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


        if (a.hasValue(R.styleable.PinMenu_pin_icon_color)) {
            mIconColor = a.getColor(R.styleable.PinMenu_pin_icon_color, Color.WHITE);
        }

        if (a.hasValue(R.styleable.PinMenu_pin_selected_icon_color)) {
            mSelectedIconColor = a.getColor(R.styleable.PinMenu_pin_selected_icon_color, Color.WHITE);
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
        setColorFilter(mSelectedIconColor, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void unSelected() {
        mBackgroundDrawable.setColor(mBackgroundColor);
        setBackground(mBackgroundDrawable);
        setColorFilter(mIconColor, android.graphics.PorterDuff.Mode.SRC_IN);
    }
}



