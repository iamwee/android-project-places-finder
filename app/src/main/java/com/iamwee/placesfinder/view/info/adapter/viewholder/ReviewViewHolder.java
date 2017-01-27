package com.iamwee.placesfinder.view.info.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iamwee.placesfinder.R;

/**
 * Created by zeon on 1/27/17.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    public TextView tvCodename;
    public TextView tvEmail;
    public TextView tvDateReview;
    public TextView tvReviewMessage;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        tvCodename = (TextView) itemView.findViewById(R.id.tv_code_name);
        tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
        tvDateReview = (TextView) itemView.findViewById(R.id.tv_date);
        tvReviewMessage = (TextView) itemView.findViewById(R.id.tv_review);
    }
}
