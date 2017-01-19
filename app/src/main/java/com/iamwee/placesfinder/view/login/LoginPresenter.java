package com.iamwee.placesfinder.view.login;

import android.os.Bundle;

import com.google.gson.Gson;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.dao.LoginResponse;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.Contextor;
import com.iamwee.placesfinder.util.NetworkUtil;
import com.iamwee.placesfinder.util.SessionUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class LoginPresenter extends BasePresenter<LoginContractor.View>
        implements LoginContractor.Presenter, Callback<LoginResponse> {

    private Call<LoginResponse> call;


    private LoginPresenter(LoginContractor.View view) {
        super(view);
        getView().setPresenter(this);
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
        return new Bundle();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {

    }

    @Override
    public void login(String email, String password) {
        if (!isFormValidated(email, password)) {
            getView().onLoginFailure(
                    getContext().getString(R.string.msg_please_enter_email_password)
            );
            return;
        } else if (!NetworkUtil.isNetworkAvailable(Contextor.getInstance().getContext())) {
            getView().onNetworkConnectionFailure();
            return;
        }
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "password")
                .add("email", email)
                .add("password", password)
                .build();

        call = HttpManager.getServices().login(body);
        call.enqueue(this);
        getView().onServiceExecuting();
    }


    private boolean isFormValidated(String email, String password) {
        return !(email.isEmpty() || password.isEmpty());
    }

    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        if (response.isSuccessful()) {
            SessionUtil.createSession(response.body());
            getView().onLoginSuccess();
        } else if (response.code() == HttpManager.BAD_REQUEST) {
            try {
                ServerResponse serverResponse = new Gson().
                        fromJson(response.errorBody().string(), ServerResponse.class);
                getView().onLoginFailure(serverResponse.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getView().onServicePostExecute();
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        String error = NetworkUtil.analyzeNetworkException(t);
        if(error != null) getView().onShowToastMessage(error);
        else t.printStackTrace();
        getView().onServicePostExecute();
    }


    @Override
    public void cancelCallLogin() {
        if (call.isExecuted()) call.cancel();
        getView().onShowToastMessage(getContext().getString(R.string.msg_cancelled));
    }
}
