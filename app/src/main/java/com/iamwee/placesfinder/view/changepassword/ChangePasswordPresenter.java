package com.iamwee.placesfinder.view.changepassword;

import android.os.Bundle;

import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.ServerResponse;
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


class ChangePasswordPresenter extends BasePresenter<ChangePasswordContractor.View>
        implements ChangePasswordContractor.Presenter, Callback<ServerResponse> {

    private Call<ServerResponse> call;

    private ChangePasswordPresenter(ChangePasswordContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    public static ChangePasswordPresenter newInstance(ChangePasswordContractor.View view) {
        return new ChangePasswordPresenter(view);
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
    public void changePassword(String newPassword, String confirmPassword) {
        if(newPassword.isEmpty() || confirmPassword.isEmpty()){
            getView().onShowToastMessage("Please input your new password and confirm password.");
        } else if (!newPassword.equals(confirmPassword)){
            getView().onShowToastMessage("New password and confirm password must match.");
        } else {
            RequestBody body = new FormBody.Builder()
                    .add("secret", SessionUtil.getSecretCode())
                    .add("token", SessionUtil.getToken())
                    .add("new_password", newPassword)
                    .add("confirm_password", confirmPassword)
                    .build();

            call = HttpManager.getServices().changePassword(body);
            call.enqueue(this);
            getView().onServiceExecuting();

        }
    }


    @Override
    public void cancelCall() {
        if(call != null && call.isExecuted()) call.cancel();
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        if(response.isSuccessful()){
            String message = response.body().getMessage();
            getView().onChangePasswordSuccess(message);
        } else {
            try {
                String message = GsonUtil.getInstance()
                        .fromJson(response.errorBody().string(), ServerResponse.class)
                        .getMessage();
                getView().onShowToastMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getView().onServicePostExecute();
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        String error = NetworkUtil.analyzeNetworkException(t);
        if(error != null) getView().onShowToastMessage(error);
        else t.printStackTrace();
        getView().onServicePostExecute();
    }
}
