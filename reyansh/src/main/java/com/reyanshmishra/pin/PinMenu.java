package com.reyanshmishra.pin;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

public class PinMenu extends android.support.v7.widget.AppCompatImageView {

    public String getPinName() {
        return mPinName;
    }

    private String mPinName;

    public PinMenu(Context context) {
        super(context);
    }

    public PinMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PinMenu, defStyleAttr, 0);
        mPinName = a.getString(R.styleable.PinMenu_pin_name);
        a.recycle();
    }
}



