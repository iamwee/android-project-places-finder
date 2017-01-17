package com.iamwee.placesfinder.view.profile;

import android.content.Intent;
import android.os.Bundle;

import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.utilities.GsonUtil;
import com.iamwee.placesfinder.utilities.SessionUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ProfilePresenter implements ProfileContractor.Presenter, Callback<ServerResponse> {

    private ProfileContractor.View view;
    private Call<ServerResponse> call;

    private ProfilePresenter(ProfileContractor.View view){
        this.view = view;
        this.view.setPresenter(this);
    }

    static ProfilePresenter newInstance(ProfileContractor.View view){
        return new ProfilePresenter(view);
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
    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void saveProfile(String codeName) {
        RequestBody body = new FormBody.Builder()
                .add("secret", SessionUtil.getSecretCode())
                .add("token", SessionUtil.getToken())
                .add("code_name", codeName)
                .build();

        call = HttpManager.getInstance().getServices().updateProfile(body);
        call.enqueue(this);
    }

    @Override
    public void cancelCall() {
        if(call != null && call.isExecuted()) call.cancel();
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        if(response.isSuccessful()) {
            view.onProfileSaved(response.body().getMessage());
        } else if (response.code() == HttpManager.BAD_REQUEST) {
            try {
                ServerResponse serverResponse = GsonUtil.getInstance()
                        .fromJson(
                                response.errorBody().string(),
                                ServerResponse.class);
                view.onShowToastMessage(serverResponse.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {

    }
}
