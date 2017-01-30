package com.iamwee.placesfinder.view.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.util.SessionUtil;
import com.iamwee.placesfinder.view.login.LoginActivity;
import com.iamwee.placesfinder.view.suggest.choosephoto.ChoosePhotoActivity;
import com.iamwee.placesfinder.view.writereview.WriteReviewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PlaceInfoActivity extends PlacesFinderActivity {

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        setupPlaceData();
        setupToolbar();
        setupFragment(savedInstanceState);
        setupView();
    }

    private void setupFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceInfoFragment.newInstance(place))
                    .commit();
        }
    }

    private void setupPlaceData() {
        Intent intent = getIntent();
        place = intent.getParcelableExtra("place");
    }

    @Override
    protected void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(place.getName());
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
        if (SessionUtil.hasLoggedIn()) {
            switch (event.getStatus()) {
                case OpenActivity.WRITE_REVIEW:
                    Intent intent = new Intent(this, WriteReviewActivity.class);
                    intent.putExtra("place", place);
                    openActivity(intent);
                    break;
                case OpenActivity.CHOOSE_PHOTO:
                    startActivityForResult(new Intent(this, ChoosePhotoActivity.class), 1);
                    break;
                case OpenActivity.DIRECTION:
                    Intent directionIntent = new Intent(this, PlaceDirectionActivity.class);
                    directionIntent.putExtra("place", place);
                    startActivity(directionIntent);
                    overridePendingTransition(0, 0);
            }
        } else {
            openActivity(new Intent(this, LoginActivity.class), true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("place", place);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            place = savedInstanceState.getParcelable("data");
        }
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
