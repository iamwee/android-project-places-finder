package com.iamwee.placesfinder.common;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.iamwee.placesfinder.R;

public class PlacesFinderActivity extends AppCompatActivity {

    public static final long SHORT_DELAY = 200;
    public static final long DELAY = 400;

    private boolean delay = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat
                    .getColor(this, R.color.colorPrimaryDark));
        }
    }

    protected void initView() {

    }

    protected void setupToolbar() {

    }

    protected void setupView() {
    }

    protected void openActivity(Intent intent) {
        openActivity(intent, SHORT_DELAY, false);
    }

    protected void openActivity(Intent intent, long duration) {
        openActivity(intent, duration, false);
    }

    protected void openActivity(Intent intent, boolean hasFinish) {
        openActivity(intent, SHORT_DELAY, hasFinish);
    }

    protected void openActivityNonDelay(Intent intent) {
        openActivityNonDelay(intent, false);
    }

    protected void openActivityNonDelay(Intent intent, boolean hasFinish) {
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (hasFinish) finish();
    }

    protected void openActivity(final Intent intent, long duration,
                                final boolean hasFinish) {
        if (isDelay()) return;
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                if (hasFinish) finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                toggle();
            }
        }, duration);
        toggle();
    }

    private boolean isDelay() {
        return this.delay;
    }

    private void toggle() {
        delay = !delay;
    }
}
