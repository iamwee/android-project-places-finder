package com.iamwee.placesfinder.view.profile;

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

class ProfilePresenter implements ProfileContractor.Presenter, Callback<UserProfile> {

    private ProfileContractor.View view;
    private Call<UserProfile> call;

    private ProfilePresenter(ProfileContractor.View view){
        this.view = view;
        this.view.setPresenter(this);
    }

    static ProfilePresenter newInstance(ProfileContractor.View view){
        return new ProfilePresenter(view);
    }

    @Override
    public void onStart() {
        getCurrentProfile();
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

    @Override
    public void getCurrentProfile() {
        RequestBody body = new FormBody.Builder()
                .add("secret", SessionUtil.getSecretCode())
                .add("token", SessionUtil.getToken())
                .build();
        call = HttpManager.getInstance().getServices().getCurrentProfile(body);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

    }

    @Override
    public void onFailure(Call<UserProfile> call, Throwable t) {

    }
}
