package com.iamwee.placesfinder.view.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.dao.Place;

public class PlaceInfoActivity extends PlacesFinderActivity {

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        setupPlaceData();
        setupToolbar();
        setupView();
    }

    private void setupPlaceData() {
        Intent intent = getIntent();
        place = intent.getParcelableExtra("place");
        getSupportActionBar().setTitle(place.getName());
    }

    @Override
    protected void setupToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setupView() {

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
