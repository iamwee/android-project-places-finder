package com.iamwee.placesfinder.view.main.pager.nearby;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.manager.permission.PermissionManager;
import com.iamwee.placesfinder.util.LocationUtil;


public class PlaceNearbyFragment extends PlacesFinderFragment<PlaceNearbyContractor.Presenter>
        implements PlaceNearbyContractor.View, OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap googleMap;

    public PlaceNearbyFragment() {

    }

    public static PlaceNearbyFragment newInstance() {
        PlaceNearbyFragment fragment = new PlaceNearbyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PlaceNearbyPresenter.newInstance(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_places_map, container, false);
        initView(rootView);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onNetworkConnectionFailure() {

    }

    @Override
    public void onShowToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    protected void setupView(View rootView) {
        if (googleMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment)
                    getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setupGoogleMapProperty();
        if(LocationUtil.isLocationAvailable(getActivity())){
            getPresenter().initLocationServiceClient();
        } else {
            LocationUtil.showErrorDialogMessage(getActivity());
        }
    }

    private void setupGoogleMapProperty() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(LocationUtil.getCurrentLocation())
                .zoom(16)
                .tilt(60)
                .build();
        googleMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition)
                ,400, null);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        getPresenter().getPlaceByName(marker);
        //TODO: implement when marker has clicked here.
    }

    @Override
    public void onClearMarker() {
        googleMap.clear();
    }

    @Override
    public void onAddMarker() {
        //TODO: implement to add marker here.
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onAnyPermissionDenied(String message) {
        PermissionManager.showPermissionRequestDeniedDialog(getActivity(), message);
    }
}