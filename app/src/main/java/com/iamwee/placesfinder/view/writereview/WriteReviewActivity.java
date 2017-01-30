package com.iamwee.placesfinder.view.writereview;

import android.os.Bundle;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.event.OpenActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WriteReviewActivity extends PlacesFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        Place place = getIntent().getParcelableExtra("place");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, WriteReviewFragment.newInstance(place))
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
    public void onSubmitted(OpenActivity event){
        if (event.getStatus() == OpenActivity.FINISH){
            finish();
        }
    }
}
