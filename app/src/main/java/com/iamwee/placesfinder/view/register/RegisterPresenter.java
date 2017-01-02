package com.iamwee.placesfinder.view.register;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Zeon on 2/1/2560.
 */

class RegisterPresenter implements RegisterContractor.Presenter {

    private RegisterContractor.View view;

    private RegisterPresenter(RegisterContractor.View view){
        this.view = view;
        this.view.setPresenter(this);
    }

    static RegisterPresenter newInstance(RegisterContractor.View view){
        return new RegisterPresenter(view);
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
