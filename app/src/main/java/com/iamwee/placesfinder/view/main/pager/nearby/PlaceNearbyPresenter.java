package com.iamwee.placesfinder.view.main.pager.nearby;

import android.Manifest;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.manager.permission.PermissionManager;
import com.iamwee.placesfinder.manager.permission.PermissionResult;
import com.iamwee.placesfinder.util.GeoCoderUtil;
import com.iamwee.placesfinder.util.LocationUtil;
import com.iamwee.placesfinder.util.NetworkUtil;
import com.iamwee.placesfinder.util.PlaceUtil;
import com.iamwee.placesfinder.util.SessionUtil;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class PlaceNearbyPresenter extends BasePresenter<PlaceNearbyContractor.View>
        implements PlaceNearbyContractor.Presenter, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        PermissionManager.PermissionCallback, Callback<ArrayList<Place>>, GeoCoderUtil.Callback {

    private GoogleApiClient googleApiClient;
    private ArrayList<Place> places;
    private Call<ArrayList<Place>> call;
    private GeoCoderUtil geoCoderUtil;

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
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("places", places);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        places = savedInstanceState.getParcelableArrayList("places");
        getPlacesData();
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
        Log.e(getClass().getSimpleName(), String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(getClass().getSimpleName(),
                connectionResult.getErrorCode() + " : " + connectionResult.getErrorMessage());
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationUtil.saveLocation(location);
        if (geoCoderUtil == null)
            geoCoderUtil = new GeoCoderUtil.Builder()
                    .withLocation(new LatLng(location.getLatitude(), location.getLongitude()))
                    .build();
        geoCoderUtil.findAddress(this);
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
            sendPermissionDeniedMessage();
        } else {
            sendPermissionDeniedMessage();
        }
    }

    private void sendPermissionDeniedMessage() {
        getView().onAnyPermissionDenied(getContext().getString(R.string.error_we_need_to_access_to_location));
    }

    @Override
    public void getPlacesData() {
        if (NetworkUtil.isNetworkAvailable()) {
            getPlacesFromServer();
        } else {
            places = PlaceUtil.load();
            addMarkerIntoMap();
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
    public void onResponse(Call<ArrayList<Place>> call, Response<ArrayList<Place>> response) {
        if (response.isSuccessful()) {
            this.places = response.body();
            PlaceUtil.cache(places);
            getView().onClearMarker();
            addMarkerIntoMap();
        }
    }

    private void addMarkerIntoMap() {
        getView().onClearMarker();
        for (Place place : places) {
            getView().onAddMarker(place);
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Place>> call, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onFindAddressResult(Address address, String addressName) {
        LocationUtil.saveAddress(addressName);
    }

    @Override
    public void onFindAddressFailure(Throwable t) {
        t.printStackTrace();
    }
}
