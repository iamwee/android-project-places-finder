package com.iamwee.placesfinder.view.info;

import com.iamwee.placesfinder.view.info.model.ReviewItem;
import com.iamwee.placesfinder.view.info.model.SectionItem;
import com.iamwee.placesfinder.view.info.model.SummaryItem;

/**
 * Created by zeon on 1/24/17.
 */

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
