package com.iamwee.placesfinder.view.register;

import android.os.Bundle;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;

public class RegisterActivity extends PlacesFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (savedInstanceState == null){
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
}
