package com.iamwee.placesfinder.view.suggest.choosephoto.model;

import com.iamwee.placesfinder.view.suggest.choosephoto.ChoosePhotoType;

/**
 * Created by zeon on 1/25/17.
 */

public class PhotoItem extends BaseChoosePhotoItem {

    private String path;

    public PhotoItem(String path) {
        super(ChoosePhotoType.PHOTO_TYPE);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
