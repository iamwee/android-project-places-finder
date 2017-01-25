package com.iamwee.placesfinder.view.suggest.choosephoto.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.iamwee.placesfinder.R;

/**
 * Created by zeon on 1/25/17.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivImg;

    public PhotoViewHolder(View itemView) {
        super(itemView);
        ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
    }
}
