package com.iamwee.placesfinder.view.logout;

import android.content.Intent;
import android.os.Bundle;

import com.iamwee.placesfinder.dao.ServerResponse;
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

class LogoutPresenter implements LogoutContractor.Presenter, Callback<ServerResponse> {

    private LogoutContractor.View view;

    private LogoutPresenter(LogoutContractor.View view) {
        this.view = view;
        this.view.setPresenter(this);
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
    public void logout() {
        RequestBody body = new FormBody.Builder()
                .add("token", SessionUtil.getToken())
                .add("secret", SessionUtil.getSecretCode())
                .build();

        HttpManager.getInstance().getServices().logout(body).enqueue(this);
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        SessionUtil.destroySession();
        view.onPostLogout();
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        SessionUtil.destroySession();
        view.onPostLogout();
    }
}
