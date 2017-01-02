package com.iamwee.placesfinder.view.main;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Zeon on 2/1/2560.
 */

class MainPresenter implements MainContractor.Presenter {

    private MainContractor.View view;

    private MainPresenter(MainContractor.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    static MainPresenter newInstance(MainContractor.View view) {
        return new MainPresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public Bundle onSaveInstanceState() {
        return new Bundle();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {

    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void show() {
        view.onShowToast("Hello world");
    }
}
