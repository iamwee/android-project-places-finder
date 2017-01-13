package com.iamwee.placesfinder.view.login;

import android.content.Intent;
import android.os.Bundle;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.common.event.OpenActivity;
import com.iamwee.placesfinder.view.main.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LoginActivity extends PlacesFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, LoginFragment.newInstance())
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
        //TODO: Fix issue : after login success it not finish current activity
        if (event.getStatus() == OpenActivity.MAIN_ACTIVITY) {
            if (event.isDelay()) {
                openActivity(new Intent(this, MainActivity.class), event.isFinish());
            } else {
                openActivityNonDelay(new Intent(this, MainActivity.class), event.isFinish());
            }
        }
    }
}
