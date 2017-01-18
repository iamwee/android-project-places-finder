package com.iamwee.placesfinder;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.iamwee.placesfinder.util.Contextor;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Zeon on 2/1/2560.
 */

public class PlacesFinderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
