package com.iamwee.placesfinder.view.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.event.SwipeRefresh;
import com.iamwee.placesfinder.util.SessionUtil;
import com.iamwee.placesfinder.view.login.LoginActivity;
import com.iamwee.placesfinder.view.photo.PhotoListActivity;
import com.iamwee.placesfinder.view.report.ReportActivity;
import com.iamwee.placesfinder.view.writereview.WriteReviewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PlaceInfoActivity extends PlacesFinderActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private Place place;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        initView();
        setupView();
        setupPlaceData();
        setupToolbar();
        setupFragment(savedInstanceState);
    }

    @Override
    protected void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );
    }

    @Override
    protected void setupView() {
        swipeRefreshLayout.setOnRefreshListener(this);
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
    public void onRefreshed(SwipeRefresh event) {
        if (event.getStatus() == SwipeRefresh.DISMISS) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Subscribe
    public void onOpenActivity(OpenActivity event) {

        if (event.getStatus() == OpenActivity.PHOTO_LIST_ACTIVITY) {
            Intent intent = new Intent(this, PhotoListActivity.class);
            intent.putExtra("place", place);
            openActivity(intent);
            return;
        } else if (event.getStatus() == OpenActivity.REPORT){
            Intent intent = new Intent(this, ReportActivity.class);
            intent.putExtra("place", place);
            openActivity(intent);
            return;
        }

        if (SessionUtil.hasLoggedIn()) {
            switch (event.getStatus()) {
                case OpenActivity.WRITE_REVIEW_ACTIVITY:
                    Intent intent = new Intent(this, WriteReviewActivity.class);
                    intent.putExtra("place", place);
                    openActivity(intent);
                    break;
                case OpenActivity.DIRECTION_ACTIVITY:
                    Intent directionIntent = new Intent(this, PlaceDirectionActivity.class);
                    directionIntent.putExtra("place", place);
                    startActivity(directionIntent);
                    overridePendingTransition(0, 0);
                    break;
                case OpenActivity.LOGIN_ACTIVITY:
                    openActivity(new Intent(this, LoginActivity.class), true);
            }
        } else {
            openActivity(new Intent(this, LoginActivity.class), true);
        }
    }

    @Subscribe
    public void onSetupPlace(Place place) {
        this.place = place;
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

    @Override
    public void onRefresh() {
        EventBus.getDefault().post(new SwipeRefresh(SwipeRefresh.REFRESH));
    }
}
