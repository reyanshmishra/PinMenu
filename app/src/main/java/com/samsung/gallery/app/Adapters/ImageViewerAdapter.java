package com.samsung.gallery.app.Adapters;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.samsung.gallery.app.Models.ImageModel;
import com.samsung.gallery.app.R;

import java.util.ArrayList;

/**
 * Created by REYANSH on 7/19/2017.
 */

public class ImageViewerAdapter extends RecyclerView.Adapter<ImageViewerAdapter.ItemHolder> {
    private ArrayList<ImageModel> mImageModels;

    public ImageViewerAdapter(ArrayList<ImageModel> imageModels) {
        mImageModels = imageModels;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_viewer_layout, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ImageLoader.getInstance().displayImage(mImageModels.get(position).imageUri, holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mImageModels == null ? 0 : mImageModels.size();
    }

    public void updateData(ArrayList<ImageModel> imageModels) {
        mImageModels = imageModels;
        notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public ItemHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image_view);
            DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
            params.width = metrics.widthPixels;
            params.height = metrics.widthPixels;
            mImageView.setLayoutParams(params);
        }
    }
}
