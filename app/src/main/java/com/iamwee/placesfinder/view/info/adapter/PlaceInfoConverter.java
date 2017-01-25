package com.iamwee.placesfinder.view.info.adapter;

import com.iamwee.placesfinder.view.info.adapter.model.MapSection;
import com.iamwee.placesfinder.view.info.adapter.model.MenuSection;
import com.iamwee.placesfinder.view.info.adapter.model.ReviewItem;
import com.iamwee.placesfinder.view.info.adapter.model.SectionItem;
import com.iamwee.placesfinder.view.info.adapter.model.SummaryItem;


public class PlaceInfoConverter {

    public static SectionItem makeHeaderSection() {
        return new SectionItem();
    }

    public static SummaryItem createSummarySection() {
        return new SummaryItem();
    }

    public static MapSection makeMapSection() {
        return new MapSection();
    }

    public static MenuSection makeMenuSection() {
        return new MenuSection();
    }

    public static ReviewItem createReviewSection() {
        return new ReviewItem();
    }
}
