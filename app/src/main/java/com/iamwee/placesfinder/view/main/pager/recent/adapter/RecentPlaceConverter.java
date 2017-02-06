package com.iamwee.placesfinder.view.main.pager.recent.adapter;

import com.iamwee.placesfinder.dao.Place;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zeon on 2/5/17.
 */

public class RecentPlaceConverter {

    public static List<PlaceItem> createPlaceItemAll(List<Place> places) {
        Collections.reverse(places);
        List<PlaceItem> placeItems = new ArrayList<>();
        for (Place place : places) {
            PlaceItem item = new PlaceItem();
            item.setData(place);
            placeItems.add(item);
        }
        return placeItems;
    }

    public static PlaceNotFoundItem createPlaceNotFound() {
        return new PlaceNotFoundItem();
    }

}
