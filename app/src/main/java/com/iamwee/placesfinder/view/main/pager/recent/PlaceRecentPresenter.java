package com.iamwee.placesfinder.view.main.pager.recent;

import android.os.Bundle;
import android.util.Log;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.GsonUtil;
import com.iamwee.placesfinder.util.NetworkUtil;
import com.iamwee.placesfinder.util.SessionUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class PlaceRecentPresenter extends BasePresenter<PlaceRecentContractor.View>
        implements PlaceRecentContractor.Presenter, Callback<ArrayList<Place>> {

    private PlaceRecentPresenter(PlaceRecentContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    static PlaceRecentPresenter newInstance(PlaceRecentContractor.View view) {
        return new PlaceRecentPresenter(view);
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
    public void getPlacesData() {
        if (NetworkUtil.isNetworkAvailable(getContext())) {
            HttpManager.getServices().getAllPlace(
                    SessionUtil.getSecretCode(),
                    SessionUtil.getToken()
            ).enqueue(this);
        } else {
            getView().onRefreshed();
            getView().onNetworkConnectionFailure();
        }
    }

    @Override
    public void onResponse(Call<ArrayList<Place>> call, Response<ArrayList<Place>> response) {
        getView().onRefreshed();
        if (response.isSuccessful()) {
            getView().onUpdatePlacesData(response.body());
        } else if (response.code() == HttpManager.BAD_REQUEST) {
            try {
                ServerResponse serverResponse = GsonUtil.getInstance()
                        .fromJson(response.errorBody().string(), ServerResponse.class);
                Log.e(getClass().getSimpleName(), serverResponse.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (response.code() == HttpManager.UNAUTHORIZED) {
            getView().onShowToastMessage(getContext().getString(R.string.error_session_expired));
            EventBus.getDefault().post(new OpenActivity(OpenActivity.LOGIN_ACTIVITY));
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Place>> call, Throwable t) {
        getView().onRefreshed();
    }
}
