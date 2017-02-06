package com.iamwee.placesfinder.view.info.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.iamwee.placesfinder.R;

/**
 * Created by zeon on 2/4/17.
 */

public class MorePhotoHeaderViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout rootLayout;

    public MorePhotoHeaderViewHolder(View itemView) {
        super(itemView);
        rootLayout = (LinearLayout) itemView.findViewById(R.id.more_photo_header);
    }
}
