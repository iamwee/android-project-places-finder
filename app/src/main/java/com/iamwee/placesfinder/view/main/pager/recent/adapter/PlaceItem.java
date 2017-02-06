package com.iamwee.placesfinder.view.main.pager.recent.adapter;

import com.iamwee.placesfinder.dao.Place;

class PlaceItem extends BaseRecentPlaceItem {

    private Place place;

    PlaceItem() {
        super(RecentPlaceType.PLACE_TYPE);
    }

    Place getData() {
        return place;
    }

    void setData(Place place) {
        this.place = place;
    }
}
