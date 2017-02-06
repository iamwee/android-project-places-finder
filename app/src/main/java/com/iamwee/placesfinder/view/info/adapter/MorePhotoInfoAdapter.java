package com.iamwee.placesfinder.view.info.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.manager.HttpManager;

import java.util.List;

public class MorePhotoInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> photoLists;

    public MorePhotoInfoAdapter(List<String> photoLists) {
        this.photoLists = photoLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
        Glide.with(photoViewHolder.itemView.getContext())
                .load(HttpManager.IMAGE_BASE_URL + photoLists.get(position))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photoViewHolder.ivImg);
        photoViewHolder.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OK", "OK");
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoLists.size();
    }

    private class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImg;

        PhotoViewHolder(View itemView) {
            super(itemView);
            ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
