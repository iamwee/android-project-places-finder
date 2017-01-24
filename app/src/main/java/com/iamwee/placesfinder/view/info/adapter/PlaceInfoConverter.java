package com.iamwee.placesfinder.view.info.adapter;

import com.iamwee.placesfinder.view.info.adapter.model.ReviewItem;
import com.iamwee.placesfinder.view.info.adapter.model.SectionItem;
import com.iamwee.placesfinder.view.info.adapter.model.SummaryItem;


public class PlaceInfoConverter {

    public static SectionItem createSection() {
        return new SectionItem();
    }

    public static SummaryItem createSummary() {
        return new SummaryItem();
    }

    public static ReviewItem createReview() {
        return new ReviewItem();
    }
}
