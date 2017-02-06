package com.iamwee.placesfinder.view.main.pager.recent.adapter;

/**
 * Created by zeon on 2/5/17.
 */

public class BaseRecentPlaceItem {

    private int type;

    public BaseRecentPlaceItem(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
