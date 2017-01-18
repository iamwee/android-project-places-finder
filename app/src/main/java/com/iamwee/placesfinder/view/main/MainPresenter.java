package com.iamwee.placesfinder.view.main;

import android.os.Bundle;

import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.dao.UserProfile;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.GsonUtil;
import com.iamwee.placesfinder.util.NetworkUtil;
import com.iamwee.placesfinder.util.SessionUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class MainPresenter implements MainContractor.Presenter, Callback<UserProfile> {

    private MainContractor.View view;
    private Call<UserProfile> call;

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
        if(call != null && call.isExecuted()) call.cancel();
    }

    @Override
    public Bundle onSaveInstanceState() {
        return new Bundle();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {

    }

    @Override
    public void getCurrentProfile() {
        RequestBody body = new FormBody.Builder()
                .add("secret", SessionUtil.getSecretCode())
                .add("token", SessionUtil.getToken())
                .build();

        call = HttpManager.getServices().getCurrentProfile(body);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
        if (response.isSuccessful()) {
            SessionUtil.saveUserProfile(response.body());
        } else {
            try {
                String message = GsonUtil.getInstance()
                        .fromJson(response.errorBody().string(), ServerResponse.class)
                        .getMessage();
                view.onShowToastMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<UserProfile> call, Throwable t) {
        String error = NetworkUtil.analyzeNetworkException(t);
        if (error != null) view.onShowToastMessage(error);
        else t.printStackTrace();
    }
}
