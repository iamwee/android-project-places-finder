package com.iamwee.placesfinder.view.main;

import android.content.Intent;
import android.os.Bundle;

import com.iamwee.placesfinder.dao.UserProfile;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.utilities.SessionUtil;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Zeon on 2/1/2560.
 */

class MainPresenter implements MainContractor.Presenter, Callback<UserProfile> {

    private MainContractor.View view;
    private Call<UserProfile> getCurrentProfileCall;

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
    public void getCurrentProfile() {
        RequestBody body = new FormBody.Builder()
                .add("secret", SessionUtil.getSecretCode())
                .add("token", SessionUtil.getToken())
                .build();

        getCurrentProfileCall = HttpManager.getInstance().getServices().getCurrentProfile(body);
        getCurrentProfileCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

    }

    @Override
    public void onFailure(Call<UserProfile> call, Throwable t) {

    }
}
