package com.iamwee.placesfinder.view.login;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.dao.LoginResponse;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.utilities.Contextor;
import com.iamwee.placesfinder.utilities.NetworkUtility;
import com.iamwee.placesfinder.utilities.SessionUtil;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Zeon on 2/1/2560.
 */

public class LoginPresenter implements LoginContractor.Presenter,
        Callback<LoginResponse> {

    private LoginContractor.View view;
    private Call<LoginResponse> call;

    public LoginPresenter() {

    }

    private LoginPresenter(LoginContractor.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    static LoginPresenter newInstance(LoginContractor.View view) {
        return new LoginPresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (call.isExecuted()) call.cancel();
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
    public void login(String email, String password) {
        if (!isFormValidated(email, password)) {
            view.onLoginFailure(Contextor
                    .getInstance()
                    .getContext()
                    .getString(R.string.msg_please_enter_email_password));
            return;
        } else if (!NetworkUtility.isNetworkAvailable(Contextor.getInstance().getContext())) {
            view.onNetworkConnectionFailure();
            return;
        }
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        call = HttpManager.getInstance().getServices().login(body);
        call.enqueue(this);
        view.onShowProgressDialog();
    }


    private boolean isFormValidated(String email, String password) {
        return !(email.isEmpty() || password.isEmpty());
    }

    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        if (response.isSuccessful()) {
            SessionUtil.createSession(response.body());
            view.onLoginSuccess();
        } else if (response.code() == HttpManager.BAD_REQUEST) {
            try {
                ServerResponse serverResponse = new Gson().
                        fromJson(response.errorBody().string(), ServerResponse.class);
                view.onLoginFailure(serverResponse.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        view.onDismissProgressDialog();
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        if (t instanceof ConnectException) {
            view.onShowToast(Contextor.getInstance()
                    .getContext()
                    .getString(R.string.msg_cannot_connect_to_server));
        } else if (t instanceof SocketTimeoutException) {
            view.onShowToast(Contextor.getInstance()
                    .getContext()
                    .getString(R.string.msg_cannot_connect_to_server));
        } else {
            t.printStackTrace();
        }
        view.onDismissProgressDialog();
    }


    @Override
    public void cancelCallLogin() {
        if (call.isExecuted()) call.cancel();
        view.onShowToast(Contextor.getInstance().getContext().getString(R.string.msg_cancelled));
    }
}
