package com.samsung.gallery.app.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.samsung.gallery.app.Activities.BucketImageActivity;
import com.samsung.gallery.app.Activities.MainActivity;
import com.samsung.gallery.app.Models.BucketModel;
import com.samsung.gallery.app.R;

import java.util.ArrayList;

/**
 * Created by REYANSH on 7/19/2017.
 */

public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.ItemHolder> {
    private MainActivity mMainActivity;
    private ArrayList<BucketModel> mBucketList;

    public BucketAdapter(MainActivity mainActivity, ArrayList<BucketModel> bucketList) {
        mMainActivity = mainActivity;
        mBucketList = bucketList;
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_bucket_list, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ImageLoader.getInstance().displayImage(mBucketList.get(position).mFirstPictureInBucketUri, holder.mBucketImageView);
        holder.mBucketNameTextView.setText(mBucketList.get(position).mBucketName);
    }

    @Override
    public int getItemCount() {
        return mBucketList == null ? 0 : mBucketList.size();
    }

    public void updateData(ArrayList<BucketModel> data) {
        this.mBucketList = data;
        notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mBucketNameTextView;
        private ImageView mBucketImageView;

        public ItemHolder(View itemView) {
            super(itemView);
            mBucketImageView = itemView.findViewById(R.id.bucket_image_image_view);
            mBucketNameTextView = itemView.findViewById(R.id.bucket_name_text_view);
            itemView.setOnLongClickListener(v -> {
                mMainActivity.onLongClick(getAdapterPosition(),v);
                return true;
            });
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mMainActivity, BucketImageActivity.class);
                intent.putExtra("BUCKET_ID", mBucketList.get(getAdapterPosition()).mBucketName);
                mMainActivity.startActivity(intent);
            });
        }
    }
}
