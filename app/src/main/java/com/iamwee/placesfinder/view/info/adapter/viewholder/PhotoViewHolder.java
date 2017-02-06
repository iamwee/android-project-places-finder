package com.iamwee.placesfinder.view.info.adapter.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iamwee.placesfinder.R;

/**
 * Created by zeon on 2/4/17.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    public RecyclerView rvPhotos;

    public PhotoViewHolder(View itemView) {
        super(itemView);
        rvPhotos = (RecyclerView) itemView.findViewById(R.id.rv_photos);
        rvPhotos.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }
}
