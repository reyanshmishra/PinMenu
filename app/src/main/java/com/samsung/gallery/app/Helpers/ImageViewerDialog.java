package com.samsung.gallery.app.Helpers;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.Window;

import com.samsung.gallery.app.Adapters.ImageViewerAdapter;
import com.samsung.gallery.app.Models.ImageModel;
import com.samsung.gallery.app.R;

import java.util.ArrayList;

/**
 * This is the popup class which will display the images from particular
 * folder or say bucket.
 * But the UX is not impressive of this class which can be improved
 * How the UX is not good? Now suppose you have more then 500 images in bucket
 * and you want to change them as your scroll on screen I don't think it would look good.
 * Think about it.
 */

public class ImageViewerDialog extends Dialog {
    private int mWidth;
    private ArrayList<ImageModel> mImageModels;
    private RecyclerView mRecyclerView;
    private ImageViewerAdapter mImageViewerAdapter;

    float firstX = 0;
    boolean actionDown = true;

    public ImageViewerDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_popup);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mImageViewerAdapter = new ImageViewerAdapter(mImageModels);
        mRecyclerView.setAdapter(mImageViewerAdapter);
        show();
    }


    public boolean passTouchEvent(MotionEvent event) {

        if (actionDown) {
            firstX = event.getX();
            actionDown = false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                dismiss();
                break;
            case MotionEvent.ACTION_MOVE:
                int currentTouch = (int) event.getX();
//                When user moves finger change from it was before move to that item.
                if (currentTouch > firstX + 50 || currentTouch < firstX - 50) {
                    int in = (int) (event.getX() / mWidth);
                    mRecyclerView.smoothScrollToPosition(in);
                }

                break;
        }
        return false;
    }

    /**
     * Set the List of images of a bucket list.
     */
    public void setImages(ArrayList<ImageModel> images) {
        this.mImageModels = images;
        mImageViewerAdapter.updateData(mImageModels);
        mWidth = Resources.getSystem().getDisplayMetrics().widthPixels / mImageModels.size();
    }
}