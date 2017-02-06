package com.iamwee.placesfinder.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.view.changepassword.ChangePasswordActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ProfileActivity extends PlacesFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ProfileFragment.newInstance())
                    .commit();
        }
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
    public void onFragmentCallback(OpenActivity event) {
        switch (event.getStatus()){
            case OpenActivity.CHANGE_PASSWORD_ACTIVITY:
                openActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case OpenActivity.FINISH:
                finish();
                break;
        }
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
