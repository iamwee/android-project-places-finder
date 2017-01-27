package com.iamwee.placesfinder.view.info.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.widget.TitleView;

/**
 * Created by zeon on 1/27/17.
 */

public class SectionViewHolder extends RecyclerView.ViewHolder {

    public TitleView tvTitle;

    public SectionViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TitleView) itemView.findViewById(R.id.tv_title);
    }
}
