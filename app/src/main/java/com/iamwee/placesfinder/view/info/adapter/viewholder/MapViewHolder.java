package com.iamwee.placesfinder.view.info.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.iamwee.placesfinder.R;

/**
 * Created by zeon on 1/27/17.
 */

public class MapViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivMap;

    public MapViewHolder(View itemView) {
        super(itemView);
        ivMap = (ImageView) itemView.findViewById(R.id.iv_map);
    }
}
