package com.iamwee.placesfinder.view.report;

import android.util.Patterns;

import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.GsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ReportPresenter extends BasePresenter<ReportContractor.View>
        implements ReportContractor.Presenter, Callback<ServerResponse> {

    private Call<ServerResponse> call;

    private ReportPresenter(ReportContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    public static ReportPresenter newInstance(ReportContractor.View view){
        return new ReportPresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        cancelCall();
    }

    @Override
    public void cancelCall() {
        if (call != null && call.isExecuted()) call.cancel();
    }

    @Override
    public void sendReport(String email, String title, String description) {
        if(email.isEmpty() || title.isEmpty() || description.isEmpty()){
            getView().onShowToastMessage(getContext().getString(R.string.error_please_fill_these_form));
        } else if (!isEmailValidate(email)){
            getView().onShowToastMessage(getContext().getString(R.string.error_email_not_match));
        } else {
            RequestBody body = new FormBody.Builder()
                    .add("email", email)
                    .add("title", title)
                    .add("description", description)
                    .build();

            call = HttpManager.getServices().sendReport(body);
            call.enqueue(this);
            getView().onExecuting();
        }
    }

    private boolean isEmailValidate(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        getView().onPostExecute();
        if (response.isSuccessful()){
            getView().onShowToastMessage(response.body().getMessage());
            EventBus.getDefault().post(new OpenActivity(OpenActivity.FINISH));
        } else if (response.code() == HttpManager.BAD_REQUEST){
            try {
                ServerResponse serverResponse = GsonUtil.getInstance()
                        .fromJson(response.errorBody().string(), ServerResponse.class);
                getView().onShowToastMessage(serverResponse.getMessage());
                getView().onReportSent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        t.printStackTrace();
    }
}
