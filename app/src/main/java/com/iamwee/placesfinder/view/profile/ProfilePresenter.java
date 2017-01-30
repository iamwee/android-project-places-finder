package com.iamwee.placesfinder.view.profile;

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

class ProfilePresenter extends BasePresenter<ProfileContractor.View>
        implements ProfileContractor.Presenter, Callback<ServerResponse> {

    private Call<ServerResponse> call;

    private ProfilePresenter(ProfileContractor.View view){
        super(view);
        getView().setPresenter(this);
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
    public void saveProfile(String codeName) {
        RequestBody body = new FormBody.Builder()
                .add("secret", SessionUtil.getSecretCode())
                .add("token", SessionUtil.getToken())
                .add("code_name", codeName)
                .build();

        call = HttpManager.getServices().updateProfile(body);
        call.enqueue(this);
        getView().onExecuting();
    }

    @Override
    public void cancelCall() {
        if(call != null && call.isExecuted()) call.cancel();
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        if(response.isSuccessful()) {
            getView().onProfileSaved(response.body().getMessage());
        } else if (response.code() == HttpManager.BAD_REQUEST) {
            try {
                ServerResponse serverResponse = GsonUtil.getInstance()
                        .fromJson(
                                response.errorBody().string(),
                                ServerResponse.class);
                getView().onShowToastMessage(serverResponse.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getView().onPostExecute();
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        String error = NetworkUtil.analyzeNetworkException(t);
        if(error != null) getView().onShowToastMessage(error);
        else t.printStackTrace();
        getView().onPostExecute();
    }
}
