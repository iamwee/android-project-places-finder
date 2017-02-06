package com.iamwee.placesfinder.view.feedback;

import android.os.Bundle;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.GsonUtil;
import com.iamwee.placesfinder.util.NetworkUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class FeedbackPresenter extends BasePresenter<FeedbackContractor.View>
        implements FeedbackContractor.Presenter, Callback<ServerResponse> {


    private Call<ServerResponse> call;

    private FeedbackPresenter(FeedbackContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }


    public static FeedbackPresenter newInstance(FeedbackContractor.View view) {
        return new FeedbackPresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        cancelCall();
    }

    @Override
    public void sendFeedback(String email, String title, String feedback) {
        if (email.isEmpty() || title.isEmpty() || feedback.isEmpty()){
            getView().onShowToastMessage("Please enter information.");
        } else {
            if (NetworkUtil.isNetworkAvailable(getContext())){
                RequestBody body = new FormBody.Builder()
                        .add("email", email)
                        .add("title", title)
                        .add("feedback", feedback)
                        .build();
                call = HttpManager.getServices().sendFeedback(body);
                call.enqueue(this);
                getView().onExecuting();
            } else {
                getView().onNetworkConnectionFailure();
            }
        }

    }

    @Override
    public void cancelCall() {
        if (call != null && call.isExecuted()) call.cancel();
        getView().onShowToastMessage(getContext().getString(R.string.msg_cancelled));
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        getView().onPostExecute();
        if (response.isSuccessful()){
            getView().onFeedbackSent(response.body().getMessage());
        } else {
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
        String error = NetworkUtil.analyzeNetworkException(t);
        getView().onShowToastMessage(error != null ? error : t.getMessage());
    }
}
