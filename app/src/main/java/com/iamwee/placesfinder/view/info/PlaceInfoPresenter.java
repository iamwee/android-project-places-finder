package com.iamwee.placesfinder.view.info;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.NetworkUtil;
import com.iamwee.placesfinder.util.SessionUtil;
import com.iamwee.placesfinder.view.info.adapter.PlaceInfoConverter;
import com.iamwee.placesfinder.view.info.adapter.model.BasePlaceInfoItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class PlaceInfoPresenter extends BasePresenter<PlaceInfoContractor.View>
        implements PlaceInfoContractor.Presenter, Callback<ServerResponse> {

    private PlaceInfoPresenter(PlaceInfoContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    static PlaceInfoPresenter newInstance(PlaceInfoContractor.View view) {
        return new PlaceInfoPresenter(view);
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
    public void convertToAdapterModel(Place place) {
        List<BasePlaceInfoItem> basePlaceInfoItems = new ArrayList<>();
        PlaceInfoConverter converter = new PlaceInfoConverter();
        basePlaceInfoItems.add(converter.createHeaderSection(place));
        basePlaceInfoItems.add(converter.createTitleSection("About"));
        basePlaceInfoItems.add(converter.createSummarySection(place));
        basePlaceInfoItems.add(converter.createMapSection(new LatLng(place.getLat(), place.getLng())));
        basePlaceInfoItems.addAll(converter.createReviewSection(place.getReviews()));
        getView().onSetAdapter(basePlaceInfoItems);
    }

    @Override
    public void submitPlace(String placeId) {
        RequestBody body = new FormBody.Builder()
                .add("secret", SessionUtil.getSecretCode())
                .add("token", SessionUtil.getToken())
                .add("place_id", placeId)
                .build();
        if (NetworkUtil.isNetworkAvailable(getContext())){
            Call<ServerResponse> call = HttpManager.getServices().submitPlace(body);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        if (response.isSuccessful()){
            getView().onShowToastMessage("Submitted.");
        }
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {

    }
}
