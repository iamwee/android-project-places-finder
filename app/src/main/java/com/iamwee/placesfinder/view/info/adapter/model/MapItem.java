package com.iamwee.placesfinder.view.info.adapter.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by zeon on 1/25/17.
 */

public class MapItem extends BasePlaceInfoItem {

    private LatLng latLng;
    private String imageUrl;

    public MapItem(){
        super(PlaceInfoType.MAP_TYPE);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
