package com.iamwee.placesfinder.event;

/**
 * Created by zeon on 2/5/17.
 */

public class SwipeRefresh {

    public static final int REFRESH = 1;
    public static final int DISMISS = 2;

    private int status;

    public SwipeRefresh(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
