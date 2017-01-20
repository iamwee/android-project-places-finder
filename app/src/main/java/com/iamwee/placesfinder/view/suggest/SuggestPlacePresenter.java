package com.iamwee.placesfinder.view.suggest;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.PlaceType;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.NetworkUtil;
import com.iamwee.placesfinder.util.SessionUtil;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class SuggestPlacePresenter extends BasePresenter<SuggestPlaceContractor.View> implements
        SuggestPlaceContractor.Presenter {

    private ArrayList<PlaceType> placeTypes;
    private Call<ArrayList<PlaceType>> getPlaceTypeCall;
    private Call<ServerResponse> submitPlaceCall;

    private SuggestPlacePresenter(SuggestPlaceContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    public static SuggestPlacePresenter newInstance(SuggestPlaceContractor.View view) {
        return new SuggestPlacePresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (getPlaceTypeCall != null && getPlaceTypeCall.isExecuted()) getPlaceTypeCall.cancel();
        else if (submitPlaceCall != null && submitPlaceCall.isExecuted()) submitPlaceCall.cancel();
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("place_type", placeTypes);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {
        placeTypes = savedState.getParcelableArrayList("place_type");
    }

    @Override
    public void getTypeOfPlace() {
        if (NetworkUtil.isNetworkAvailable(getContext())) {
            RequestBody body = new FormBody.Builder()
                    .add("secret", SessionUtil.getSecretCode())
                    .add("token", SessionUtil.getToken())
                    .build();
            getPlaceTypeCall = HttpManager.getServices().getPlaceType(body);
            getPlaceTypeCall.enqueue(new Callback<ArrayList<PlaceType>>() {
                @Override
                public void onResponse(Call<ArrayList<PlaceType>> call,
                                       Response<ArrayList<PlaceType>> response) {
                    if (response.isSuccessful()) {
                        placeTypes = response.body();
                        getView().onPostGetPlaceType(placeTypes);
                        getView().onServicePostExecute();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<PlaceType>> call,
                                      Throwable t) {
                    String error = NetworkUtil.analyzeNetworkException(t);
                    if (error != null) getView().onShowToastMessage(error);
                    else t.printStackTrace();
                    getView().onServicePostExecute();
                }
            });
            getView().onServiceExecuting();
        } else {
            getView().onNetworkConnectionFailure();
        }
    }

    @Override
    public void cancelCall() {
        if (getPlaceTypeCall != null && getPlaceTypeCall.isExecuted()) getPlaceTypeCall.cancel();
    }

    @Override
    public void submitPlace(String nameText, LatLng latLng, String typeText,
                            String address,
                            String detail) {
        String typeId = "";
        for (PlaceType placeType : placeTypes) {
            if (placeType.getTypeName().equals(typeText)) {
                typeId = placeType.getId();
                break;
            }
        }

        RequestBody body = new FormBody.Builder()
                .add("secret", SessionUtil.getSecretCode())
                .add("token", SessionUtil.getToken())
                .add("name", nameText)
                .add("lat", latLng.latitude + "")
                .add("lng", latLng.longitude + "")
                .add("type", typeId)
                .add("address", address)
                .add("detail", detail)
                .build();

        submitPlaceCall = HttpManager.getServices().suggestPlace(body);
        submitPlaceCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    getView().onPlaceSubmitted();
                }
                getView().onServicePostExecute();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                String error = NetworkUtil.analyzeNetworkException(t);
                if (error != null) getView().onShowToastMessage(error);
                else t.printStackTrace();
                getView().onServicePostExecute();
            }
        });

        getView().onServiceExecuting();

    }
}
