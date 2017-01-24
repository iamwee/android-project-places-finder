package com.iamwee.placesfinder.view.info.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.iamwee.placesfinder.view.info.adapter.model.BasePlaceInfoItem;

import java.util.List;


public class PlaceInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BasePlaceInfoItem> basePlaceInfoItems;

    public PlaceInfoAdapter(List<BasePlaceInfoItem> basePlaceInfoItems) {
        this.basePlaceInfoItems = basePlaceInfoItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return basePlaceInfoItems.size();
    }
}
