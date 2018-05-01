package com.reyansh.gallery.app;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemHolder> {


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Random rnd = new Random();
        holder.mImageView.setBackgroundColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        holder.mTextView.setText("Item No. " + position);

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public ItemHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_image_view);
            mTextView = itemView.findViewById(R.id.image_name);
        }
    }
}
