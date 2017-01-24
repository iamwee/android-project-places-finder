package com.iamwee.placesfinder.view.info.adapter.model;

/**
 * Created by zeon on 1/24/17.
 */

public class BasePlaceInfoItem {

    private int type;

    public BasePlaceInfoItem() {
    }

    public BasePlaceInfoItem(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
