package com.iamwee.placesfinder.view.register;

import android.content.Intent;
import android.os.Bundle;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.common.event.OpenActivity;
import com.iamwee.placesfinder.view.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class RegisterActivity extends PlacesFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, RegisterFragment.newInstance())
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
        int action = event.getStatus();
        if (action == OpenActivity.LOGIN_ACTIVITY) {
            openActivity(new Intent(this, LoginActivity.class), true);
        }
    }
}
