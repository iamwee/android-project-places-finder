package com.iamwee.placesfinder.view.info.adapter.model;

import com.iamwee.placesfinder.dao.Place;

/**
 * Created by zeon on 1/27/17.
 */

public class HeaderItem extends BasePlaceInfoItem {

    private String imageUrl;
    private Place data;
    private String summary;

    public HeaderItem() {
        super(PlaceInfoType.HEADER_TYPE);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Place getData() {
        return data;
    }

    public void setData(Place data) {
        this.data = data;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }
}
