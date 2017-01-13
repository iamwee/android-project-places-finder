package com.iamwee.placesfinder.view.main.pager.profile;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Zeon on 2/1/2560.
 */

class ProfilePresenter implements ProfileContractor.Presenter {

    private ProfileContractor.View view;

    private ProfilePresenter(ProfileContractor.View view){
        this.view = view;
        this.view.setPresenter(this);
    }

    static ProfilePresenter newInstance(ProfileContractor.View view){
        return new ProfilePresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public Bundle onSaveInstanceState() {
        return null;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {

    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {

    }
}
