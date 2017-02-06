package com.iamwee.placesfinder.view.photo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.manager.HttpManager;

import java.util.List;

class PhotoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> photoList;

    PhotoListAdapter(List<String> photoList) {
        this.photoList = photoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
        Glide.with(photoViewHolder.itemView.getContext())
                .load(HttpManager.IMAGE_BASE_URL + photoList.get(position))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photoViewHolder.ivImg);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    private class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImg;

        PhotoViewHolder(View itemView) {
            super(itemView);
            ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
