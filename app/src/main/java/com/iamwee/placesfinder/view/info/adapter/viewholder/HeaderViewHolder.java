package com.iamwee.placesfinder.view.info.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.widget.MenuView;

/**
 * Created by zeon on 1/27/17.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivImg;
    public MenuView mvSubmitPlace;
    public MenuView mvWriteReview;
    public MenuView mvAddPhoto;
    public TextView tvName;
    public TextView tvSummary;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
        mvSubmitPlace = (MenuView) itemView.findViewById(R.id.mv_submit_place);
        mvWriteReview = (MenuView) itemView.findViewById(R.id.mv_write_review);
        mvAddPhoto = (MenuView) itemView.findViewById(R.id.mv_add_photo);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvSummary = (TextView) itemView.findViewById(R.id.tv_summary);
    }
}
