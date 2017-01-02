package com.iamwee.placesfinder.common.event;

/**
 * Created by Zeon on 2/1/2560.
 */

public class OpenActivity {

    public static final int MAIN_ACTIVITY = 1;
    public static final int LOGIN_ACTIVITY = 2;
    public static final int REGISTER_ACTIVITY = 3;
    public static final int SPLASH_SCREEN_ACTIVITY = 4;

    private int status;

    public OpenActivity(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
