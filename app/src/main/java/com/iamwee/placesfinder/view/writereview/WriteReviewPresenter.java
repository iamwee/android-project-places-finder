package com.iamwee.placesfinder.view.writereview;

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

class WriteReviewPresenter extends BasePresenter<WriteReviewContractor.View>
        implements WriteReviewContractor.Presenter, Callback<ServerResponse> {


    private Call<ServerResponse> call;

    private WriteReviewPresenter(WriteReviewContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    public static WriteReviewPresenter newInstance(WriteReviewContractor.View view) {
        return new WriteReviewPresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        cancelCall();
    }

    @Override
    public void submitReview(String placeId, String reviewMessage) {
        if (reviewMessage.isEmpty()) {
            getView().onShowToastMessage("Enter review message.");
            return;
        }

        if (NetworkUtil.isNetworkAvailable(getContext())) {
            RequestBody body = new FormBody.Builder()
                    .add("secret", SessionUtil.getSecretCode())
                    .add("token", SessionUtil.getToken())
                    .add("place_id", placeId)
                    .add("review", reviewMessage)
                    .build();
            call = HttpManager.getServices().sendReview(body);
            call.enqueue(this);
            getView().onExecuting();
        } else {
            getView().onNetworkConnectionFailure();
        }
    }

    @Override
    public void cancelCall() {
        if (call != null && call.isExecuted()) call.cancel();
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        getView().onPostExecute();
        if (response.isSuccessful()) {
            getView().onReviewSubmitted(response.body().getMessage());
        } else if (response.code() == HttpManager.BAD_REQUEST) {
            try {
                ServerResponse serverResponse = GsonUtil.getInstance()
                        .fromJson(response.errorBody().string(), ServerResponse.class);
                getView().onShowToastMessage(serverResponse.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        getView().onPostExecute();
        String error = NetworkUtil.analyzeNetworkException(t);
        if (error != null) getView().onShowToastMessage(error);
    }
}
