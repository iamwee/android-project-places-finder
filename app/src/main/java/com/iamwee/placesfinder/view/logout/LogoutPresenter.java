package com.iamwee.placesfinder.view.logout;

import android.os.Bundle;

import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.PlaceUtil;
import com.iamwee.placesfinder.util.SessionUtil;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class LogoutPresenter extends BasePresenter<LogoutContractor.View>
        implements LogoutContractor.Presenter, Callback<ServerResponse> {

    private LogoutPresenter(LogoutContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    static LogoutPresenter newInstance(LogoutContractor.View view) {
        return new LogoutPresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void logout() {
        RequestBody body = new FormBody.Builder()
                .add("token", SessionUtil.getToken())
                .add("secret", SessionUtil.getSecretCode())
                .build();

        HttpManager.getServices().logout(body).enqueue(this);
        PlaceUtil.clear();
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        SessionUtil.destroySession();
        getView().onPostLogout();
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        SessionUtil.destroySession();
        getView().onPostLogout();
    }
}
