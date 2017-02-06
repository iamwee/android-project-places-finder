package com.iamwee.placesfinder.view.main.pager.recent.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.widget.PlaceView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class PlaceRecentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseRecentPlaceItem> baseItems;

    public PlaceRecentAdapter(List<BaseRecentPlaceItem> items) {
        this.baseItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RecentPlaceType.PLACE_TYPE){
            return new PlaceViewHolder(new PlaceView(parent.getContext()));
        } else {
            return new PLaceNotFoundViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.partial_place_not_found, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return baseItems.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PlaceViewHolder) {
            PlaceViewHolder placeViewHolder = (PlaceViewHolder) holder;
            PlaceItem item = (PlaceItem) baseItems.get(position);
            setupPlaceData(placeViewHolder, item);
        }
    }

    private void setupPlaceData(PlaceViewHolder holder, PlaceItem item) {
        final Place place = item.getData();
        holder.placeView.setOnClickListener(new PlaceView.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(place);
            }
        });
        holder.placeView.setName(place.getName());
        holder.placeView.setAddress(place.getAddress());
        if (place.getImages().size() > 0) {
            holder.placeView.setImageUrl(
                    HttpManager.DEV_IMAGE_BASE_URL + place.getImages().get(0)
            );
        }
    }

    @Override
    public int getItemCount() {
        return baseItems.size();
    }

    public void setData(List<BaseRecentPlaceItem> items) {
        this.baseItems = items;
        notifyDataSetChanged();
    }

    private class PlaceViewHolder extends RecyclerView.ViewHolder {

        PlaceView placeView;

        PlaceViewHolder(View itemView) {
            super(itemView);
            placeView = (PlaceView) itemView;
        }
    }

    private class PLaceNotFoundViewHolder extends RecyclerView.ViewHolder {

        PLaceNotFoundViewHolder(View itemView) {
            super(itemView);
        }
    }
}
