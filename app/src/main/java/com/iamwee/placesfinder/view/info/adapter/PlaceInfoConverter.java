package com.iamwee.placesfinder.view.info.adapter;

import com.google.android.gms.maps.model.LatLng;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.view.info.adapter.model.HeaderItem;
import com.iamwee.placesfinder.view.info.adapter.model.MapItem;
import com.iamwee.placesfinder.view.info.adapter.model.ReviewItem;
import com.iamwee.placesfinder.view.info.adapter.model.SectionItem;
import com.iamwee.placesfinder.view.info.adapter.model.SummaryItem;

import java.util.ArrayList;
import java.util.List;


public class PlaceInfoConverter {

    public SectionItem createTitleSection(String title) {
        SectionItem item = new SectionItem();
        item.setTitle(title);
        return item;
    }

    public HeaderItem createHeaderSection(Place place) {
        HeaderItem item = new HeaderItem();
        item.setData(place);
        item.setImageUrl(place.getImages().size() > 0 ? place.getImages().get(0) : "");
        return item;
    }

    public SummaryItem createSummarySection(Place place) {
        SummaryItem item = new SummaryItem();
        item.setName(place.getName());
        item.setCategory(place.getType().getTypeName());
        item.setAddress(place.getAddress());
        item.setDetail(place.getDetail());
        return item;
    }

    public MapItem createMapSection(LatLng latLng) {
        MapItem item = new MapItem();
        item.setLatLng(latLng);
        item.setImageUrl("https://maps.googleapis.com/maps/api/staticmap?center="
                + latLng.latitude + "," + latLng.longitude + "&zoom=17&size=800x320&maptype=roadmap\n" +
                "&markers=color:red%7Clabel:C%7C" + latLng.latitude + "," + latLng.longitude +
                "&key=AIzaSyBKlU1GmrS7UCTuoj8hEmt4SNav4JwgNLw");
        return item;
    }

    public List<ReviewItem> createReviewSection(List<Place.Review> reviews) {
        List<ReviewItem> reviewItems = new ArrayList<>();
        for (Place.Review review : reviews) {
            ReviewItem item = new ReviewItem();
            item.setCodeName(review.getCodeName());
            item.setEmail(review.getEmail());
            item.setDate(review.getReviewDate());
            item.setReviewMessage(review.getReview());
            reviewItems.add(item);
        }
        return reviewItems;
    }
}
