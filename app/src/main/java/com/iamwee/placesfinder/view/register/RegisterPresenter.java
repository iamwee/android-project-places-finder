package com.iamwee.placesfinder.view.register;

import android.os.Bundle;
import android.util.Patterns;

import com.google.gson.Gson;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.NetworkUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class RegisterPresenter extends BasePresenter<RegisterContractor.View>
        implements RegisterContractor.Presenter, Callback<ServerResponse> {

    private Call<ServerResponse> createAccountCall;

    private RegisterPresenter(RegisterContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    static RegisterPresenter newInstance(RegisterContractor.View view) {
        return new RegisterPresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (createAccountCall.isExecuted() && createAccountCall != null) createAccountCall.cancel();
    }

    @Override
    public void createAccount(String email, String password,
                              String confirmPassword,
                              String codeName) {

        if (!isEmailValidated(email)) {
            getView().onShowToastMessage(email + " Doesn't match.");
        } else if (!isPasswordValidated(password)) {
            getView().onShowToastMessage("Please enter password.");
        } else if (!isPasswordValidated(confirmPassword)) {
            getView().onShowToastMessage("Please enter confirm password.");
        } else if (!isPasswordAndConfirmPasswordMatched(password,
                confirmPassword)) {
            getView().onShowToastMessage("Password and Confirm password must match.");
        } else if (!isCodeNameValidated(codeName)) {
            getView().onShowToastMessage("Please enter your code name");
        } else {
            RequestBody body = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .add("confirm_password", confirmPassword)
                    .add("code_name", codeName)
                    .build();

            createAccountCall = HttpManager.getServices().createAccount(body);
            createAccountCall.enqueue(this);
            getView().onExecuting();

        }
    }

    @Override
    public boolean isEmailValidated(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean isPasswordAndConfirmPasswordMatched(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Override
    public boolean isPasswordValidated(String password) {
        return password.length() >= 7;
    }

    @Override
    public boolean isCodeNameValidated(String codeName) {
        return !codeName.isEmpty();
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        getView().onPostExecute();
        if (response.isSuccessful()) {
            getView().onCreateAccountSuccess();
        } else if (response.code() == HttpManager.BAD_REQUEST) {
            try {
                ServerResponse serverResponse = new Gson()
                        .fromJson(response.errorBody().string(), ServerResponse.class);
                getView().onShowToastMessage(serverResponse.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        String error = NetworkUtil.analyzeNetworkException(t);
        if(error != null) getView().onShowToastMessage(error);
        else t.printStackTrace();
        getView().onPostExecute();
    }

    @Override
    public void cancelCall() {
        if (createAccountCall != null && createAccountCall.isExecuted()) createAccountCall.cancel();
        getView().onShowToastMessage(getContext().getString(R.string.msg_cancelled));
    }
}
