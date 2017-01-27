package com.iamwee.placesfinder.view.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.dao.Place;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


class PlaceSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Place> placeList;

    PlaceSearchAdapter(List<Place> placeList) {
        this.placeList = placeList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_place_item_search, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final Place place = placeList.get(position);
        viewHolder.tvName.setText(place.getName());
        viewHolder.tvAddress.setText(place.getAddress());
        viewHolder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(place);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    void update(List<Place> places) {
        this.placeList = places;
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayout;
        TextView tvName;
        TextView tvAddress;

        ViewHolder(View itemView) {
            super(itemView);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.root_layout);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
        }
    }
}
