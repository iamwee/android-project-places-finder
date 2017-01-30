package com.iamwee.placesfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.util.SessionUtil;
import com.iamwee.placesfinder.view.main.MainActivity;
import com.iamwee.placesfinder.view.walk_through.WalkThroughActivity;


public class SplashScreenActivity extends PlacesFinderActivity {

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (SessionUtil.hasLoggedIn()) {
                    openActivityNonDelay(new Intent(SplashScreenActivity.this, MainActivity.class), true);
                } else {
                    openActivityNonDelay(new Intent(SplashScreenActivity.this, WalkThroughActivity.class),
                            true);
                }

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.sendEmptyMessageDelayed(1, PlacesFinderActivity.DELAY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeMessages(1);
    }
}
