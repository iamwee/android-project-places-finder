package com.iamwee.placesfinder.view.walk_through;

import android.content.Intent;
import android.os.Bundle;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.common.event.OpenActivity;
import com.iamwee.placesfinder.view.login.LoginActivity;
import com.iamwee.placesfinder.view.register.RegisterActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WalkThroughActivity extends PlacesFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, WalkThroughFragment.newInstance())
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
    public void onOpenActivity(OpenActivity event){
        int status = event.getStatus();
        if(status == OpenActivity.REGISTER_ACTIVITY){
            openActivity(new Intent(this, RegisterActivity.class), true);
        } else if(status == OpenActivity.LOGIN_ACTIVITY){
            openActivity(new Intent(this, LoginActivity.class), true);
        }
    }
}
