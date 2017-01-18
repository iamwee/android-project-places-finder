package com.iamwee.placesfinder.view.register;

import android.os.Bundle;
import android.util.Patterns;

import com.google.gson.Gson;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.utilities.Contextor;
import com.iamwee.placesfinder.utilities.NetworkUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class RegisterPresenter implements RegisterContractor.Presenter, Callback<ServerResponse> {

    private RegisterContractor.View view;
    private Call<ServerResponse> createAccountCall;

    private RegisterPresenter(RegisterContractor.View view) {
        this.view = view;
        this.view.setPresenter(this);
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
    public Bundle onSaveInstanceState() {
        return null;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {

    }

    @Override
    public void createAccount(String email, String password,
                              String confirmPassword,
                              String codeName) {

        if (!isEmailValidated(email)) {
            view.onShowToastMessage(email + " Doesn't match.");
        } else if (!isPasswordValidated(password)) {
            view.onShowToastMessage("Please enter password.");
        } else if (!isPasswordValidated(confirmPassword)) {
            view.onShowToastMessage("Please enter confirm password.");
        } else if (!isPasswordAndConfirmPasswordMatched(password,
                confirmPassword)) {
            view.onShowToastMessage("Password and Confirm password must match.");
        } else if (!isCodeNameValidated(codeName)) {
            view.onShowToastMessage("Please enter your code name");
        } else {
            RequestBody body = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .add("confirm_password", confirmPassword)
                    .add("code_name", codeName)
                    .build();

            createAccountCall = HttpManager.getInstance().getServices().createAccount(body);
            createAccountCall.enqueue(this);
            view.onServiceExecuting();

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
        view.onServicePostExecute();
        if (response.isSuccessful()) {
            view.onCreateAccountSuccess();
        } else if (response.code() == HttpManager.BAD_REQUEST) {
            try {
                ServerResponse serverResponse = new Gson()
                        .fromJson(response.errorBody().string(), ServerResponse.class);
                view.onShowToastMessage(serverResponse.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        String error = NetworkUtil.analyzeNetworkException(t);
        if(error != null) view.onShowToastMessage(error);
        else t.printStackTrace();
        view.onServicePostExecute();
    }

    @Override
    public void cancelCall() {
        if (createAccountCall != null && createAccountCall.isExecuted()) createAccountCall.cancel();
        view.onShowToastMessage(Contextor.getInstance().getContext().getString(R.string.msg_cancelled));
    }
}
