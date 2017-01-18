package com.iamwee.placesfinder.view.logout;

import android.content.Intent;
import android.os.Bundle;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.view.SplashScreenActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LogoutActivity extends PlacesFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, LogoutFragment.newInstance())
                    .commit();
        }

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onOpenActivity(OpenActivity event) {
        if (event.getStatus() == OpenActivity.SPLASH_SCREEN_ACTIVITY) {
            openActivity(new Intent(this, SplashScreenActivity.class), 1000, true);
        }
    }
}
