package com.iamwee.placesfinder.view.info.adapter.model;

/**
 * Created by zeon on 1/24/17.
 */

public class SectionItem extends BasePlaceInfoItem {

    private String title;

    public SectionItem() {
        super(PlaceInfoType.SECTION_TYPE);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
