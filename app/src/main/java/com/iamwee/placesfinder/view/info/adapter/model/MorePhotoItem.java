package com.iamwee.placesfinder.view.info.adapter.model;

import java.util.List;

/**
 * Created by zeon on 2/4/17.
 */

public class MorePhotoItem extends BasePlaceInfoItem {

    private List<String> imageUrl;

    public MorePhotoItem() {
        super(PlaceInfoType.MORE_PHOTO_TYPE);
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
