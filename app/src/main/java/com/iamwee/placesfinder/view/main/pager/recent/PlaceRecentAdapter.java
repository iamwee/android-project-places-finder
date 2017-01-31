package com.iamwee.placesfinder.view.main.pager.recent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.widget.PlaceView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;


class PlaceRecentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Place> places;

    PlaceRecentAdapter(ArrayList<Place> places) {
        Collections.reverse(places);
        this.places = places;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new PlaceView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.placeView.setOnClickListener(new PlaceView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(getItem(position));
                }
            });
            viewHolder.placeView.setName(getItem(position).getName());
            viewHolder.placeView.setAddress(getItem(position).getAddress());
            if (places.get(position).getImages().size() > 0) {
                viewHolder.placeView.setImageUrl(getItem(position).getImages().get(0));
            }
        }
    }

    private Place getItem(int position) {
        return places.get(position);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    void setPlacesData(ArrayList<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        PlaceView placeView;

        ViewHolder(View itemView) {
            super(itemView);
            placeView = (PlaceView) itemView;
        }
    }
}
