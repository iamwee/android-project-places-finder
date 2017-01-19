package com.iamwee.placesfinder.view.main.pager.nearby;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Marker;
import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.manager.permission.PermissionManager;
import com.iamwee.placesfinder.manager.permission.PermissionResult;
import com.iamwee.placesfinder.util.LocationUtil;
import com.iamwee.placesfinder.util.NetworkUtil;
import com.iamwee.placesfinder.util.SessionUtil;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class PlaceNearbyPresenter extends BasePresenter<PlaceNearbyContractor.View>
        implements PlaceNearbyContractor.Presenter, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        PermissionManager.PermissionCallback, Callback<List<Place>> {

    private GoogleApiClient googleApiClient;
    private List<Place> places;
    private Call<List<Place>> call;

    private PlaceNearbyPresenter(PlaceNearbyContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    public static PlaceNearbyPresenter newInstance(PlaceNearbyContractor.View view) {
        return new PlaceNearbyPresenter(view);
    }

    @Override
    public void onStart() {
        if (googleApiClient != null && !googleApiClient.isConnected()) googleApiClient.connect();
        getPlacesData();
    }

    @Override
    public void onStop() {
        if (googleApiClient != null && googleApiClient.isConnected()) googleApiClient.disconnect();
        if (call != null && call.isExecuted()) call.cancel();
    }

    @Override
    public Bundle onSaveInstanceState() {
        return null;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {

    }

    @Override
    public void initLocationServiceClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        if (!googleApiClient.isConnected()) googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        PermissionManager.requestPermission(Arrays.asList(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        ), this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        //TODO: get current location here.
        LocationUtil.saveCurrentLocation(location);
        getView().onLocationChanged(location);
    }

    @Override
    public void onPermissionResult(PermissionResult permissionResult) {
        if (permissionResult.areAllPermissionsGranted()) {
            getView().onEnableMyLocationButton();
            LocationRequest request = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setFastestInterval(5000)
                    .setInterval(10000);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient,
                    request,
                    this
            );
        } else if (permissionResult.isAnyPermissionPermanentlyDenied()) {
            getView().onAnyPermissionDenied("You need to access to location.");
        } else {
            getView().onAnyPermissionDenied("You need to access to location.");
        }
    }

    @Override
    public void getPlacesData() {
        if (places == null) {
            getPlacesFromServer();
        } else if (places.size() == 0) {
            getPlacesFromServer();
        }
    }

    @Override
    public void getPlacesFromServer() {
        call = HttpManager.getServices().getAllPlace(
                SessionUtil.getSecretCode(),
                SessionUtil.getToken()
        );
        call.enqueue(this);
    }

    @Override
    public Place getPlaceByName(Marker marker) {
        for (Place place : places) {
            if (marker.getTitle().equals(place.getName())) {
                return place;
            }
        }
        throw new NullPointerException("Place doesn't match from marker " + marker.getTitle());
    }

    @Override
    public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
        this.places = response.body();
    }

    @Override
    public void onFailure(Call<List<Place>> call, Throwable t) {
        String error = NetworkUtil.analyzeNetworkException(t);
        if (error == null) t.printStackTrace();
        else getView().onShowToastMessage(error);
    }
}
