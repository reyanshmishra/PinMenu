package com.samsung.gallery.app.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.samsung.gallery.app.R;

public class ColorPickerFragment extends Fragment implements View.OnTouchListener {


    private String TAG = "PickColorActivity";
    private Context mContext;
    private boolean DEBUG = false;
    private View mView;
    private ImageView mIndicatorImageView;
    private ViewGroup mViewGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_color, container, false);
        setHasOptionsMenu(true);
        mContext = getActivity().getApplicationContext();

        mViewGroup = mView.findViewById(R.id.relative_layout_image_holder);
        mIndicatorImageView = mView.findViewById(R.id.img_view_color_indicator);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setStroke(6, Color.WHITE);
        mIndicatorImageView.setBackground(drawable);

        mView.findViewById(R.id.img_view_main).setOnTouchListener(this);
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().onBackPressed();
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                updateIndicator(event.getX(), event.getY());
                break;
            default:
                break;
        }
        return true;
    }

    public void updateIndicator(float x, float y) {
        int cx = (mIndicatorImageView.getWidth() / 2);
        int cy = (mIndicatorImageView.getHeight() / 2);

        mIndicatorImageView.setX(x - cx - 22);
        mIndicatorImageView.setY(y - cy - 22);


        int deg = 0;
        int child = mViewGroup.getChildCount();

        for (int i = 0; i < child; i++) {
            View view = mViewGroup.getChildAt(i);
            if (view.getId() == R.id.img_view_main || view.getId() == R.id.img_view_color_indicator) {
                continue;
            }
            int xP = getNewX(x - cx, deg);
            int yP = getNewY(y - cy, deg);

            view.setX(xP);
            view.setY(yP);
            deg -= 20;

        }
    }


    private int getNewX(float x, double degree) {
        int r = 200;
        double radian = degree * 3.14 / 180;
        return (int) (x + r * Math.cos(radian));
    }


    private int getNewY(float y, double degree) {
        int r = 200;
        double radian = degree * 3.14 / 180;
        return (int) (y + r * Math.sin(radian));
    }

}
