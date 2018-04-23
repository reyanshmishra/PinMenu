package com.samsung.gallery.app.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.samsung.gallery.app.Models.ImageModel;
import com.samsung.gallery.app.R;

import java.util.ArrayList;

/**
 * Created by REYANSH on 7/19/2017.
 */

public class BucketImagesAdapter extends RecyclerView.Adapter<BucketImagesAdapter.ItemHolder> {


    private ArrayList<ImageModel> mImageModels;

    public BucketImagesAdapter( ArrayList<ImageModel> imageModels) {
        mImageModels = imageModels;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ImageLoader.getInstance().displayImage(mImageModels.get(position).imageUri, holder.mImageImageView);
        holder.mImageName.setText(mImageModels.get(position).imageName);
    }

    @Override
    public int getItemCount() {
        return mImageModels == null ? 0 : mImageModels.size();
    }

    public void updateData(ArrayList<ImageModel> imageModels) {
        this.mImageModels = imageModels;
        notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mImageName;
        private ImageView mImageImageView;

        public ItemHolder(View itemView) {
            super(itemView);
            mImageImageView = (ImageView) itemView.findViewById(R.id.image_image_view);
            mImageName = (TextView) itemView.findViewById(R.id.image_name);

        }
    }
}
