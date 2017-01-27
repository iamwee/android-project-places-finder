package com.iamwee.placesfinder.view.info.adapter.model;

/**
 * Created by zeon on 1/24/17.
 */

public class ReviewItem extends BasePlaceInfoItem {

    private String codeName, email, date, reviewMessage;

    public ReviewItem() {
        super(PlaceInfoType.REVIEW_TYPE);
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }
}
