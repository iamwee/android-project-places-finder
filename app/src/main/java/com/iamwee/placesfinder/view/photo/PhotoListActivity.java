package com.iamwee.placesfinder.view.photo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.dao.Place;

public class PhotoListActivity extends PlacesFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        setupToolbar();

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Place place = intent.getParcelableExtra("place");
            getSupportActionBar().setTitle("More Photo: " + place.getName());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PhotoListFragment.newInstance(place))
                    .commit();
        }
    }

    @Override
    protected void setupToolbar() {
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
