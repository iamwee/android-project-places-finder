package com.iamwee.placesfinder.event;


public class OpenActivity {

    public static final int MAIN_ACTIVITY = 1;
    public static final int LOGIN_ACTIVITY = 2;
    public static final int REGISTER_ACTIVITY = 3;
    public static final int SPLASH_SCREEN_ACTIVITY = 4;
    public static final int CHANGE_PASSWORD_ACTIVITY = 5;
    public static final int TAKE_PHOTO_ACTIVITY = 6;
    public static final int CHOOSE_PHOTO_ACTIVITY = 7;
    public static final int WRITE_REVIEW_ACTIVITY = 8;
    public static final int SUBMIT_PLACE = 9;
    public static final int DIRECTION_ACTIVITY = 10;
    public static final int PHOTO_LIST_ACTIVITY = 11;
    public static final int FINISH = 99;

    private int status;
    private boolean finish;
    private boolean delay;

    public OpenActivity(int status) {
        this.status = status;
        this.finish = false;
        this.delay = true;
    }

    public OpenActivity(int status, boolean finish) {
        this.status = status;
        this.finish = finish;
        this.delay = true;
    }

    public OpenActivity(boolean delay, int status) {
        this.delay = delay;
        this.status = status;
    }

    public OpenActivity(int status, boolean finish, boolean delay) {
        this.status = status;
        this.finish = finish;
        this.delay = delay;
    }

    public int getStatus() {
        return status;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public boolean isDelay() {
        return delay;
    }

}
