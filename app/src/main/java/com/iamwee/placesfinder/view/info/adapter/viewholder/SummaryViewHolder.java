package com.iamwee.placesfinder.view.info.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iamwee.placesfinder.R;

/**
 * Created by zeon on 1/27/17.
 */

public class SummaryViewHolder extends RecyclerView.ViewHolder {

    public TextView tvPlaceCategory;
    public TextView tvPlaceAddress;
    public TextView tvPlaceDetail;

    public SummaryViewHolder(View itemView) {
        super(itemView);
        tvPlaceCategory = (TextView) itemView.findViewById(R.id.tv_place_category);
        tvPlaceAddress = (TextView) itemView.findViewById(R.id.tv_place_address);
        tvPlaceDetail = (TextView) itemView.findViewById(R.id.tv_place_detail);
    }
}
