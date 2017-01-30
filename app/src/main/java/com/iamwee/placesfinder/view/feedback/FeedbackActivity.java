package com.iamwee.placesfinder.view.feedback;

import android.os.Bundle;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;

public class FeedbackActivity extends PlacesFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, FeedbackFragment.newInstance())
                    .commit();
        }
    }
}
