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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderFragment;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.manager.permission.PermissionManager;
import com.iamwee.placesfinder.util.LocationUtil;

import org.greenrobot.eventbus.EventBus;

import static com.iamwee.placesfinder.R.id.map;


public class PlaceNearbyFragment extends PlacesFinderFragment<PlaceNearbyContractor.Presenter>
        implements PlaceNearbyContractor.View, OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap googleMap;
    private boolean animate = false;

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
        View rootView = inflater.inflate(R.layout.fragment_nearby_place, container, false);
        initView(rootView);
        setupView(rootView);
        setupGoogleMap();
        return rootView;
    }

    @Override
    public void onNetworkConnectionFailure() {
        Toast.makeText(getActivity(), R.string.error_check_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
        if (LocationUtil.isLocationAvailable(getActivity())) {
            getPresenter().initLocationServiceClient();
        } else {
            LocationUtil.showErrorDialogMessage(getActivity());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("presenter", getPresenter().onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            getPresenter().onRestoreInstanceState(savedInstanceState.getBundle("presenter"));
    }

    private void setupGoogleMap() {
        if (googleMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment)
                    getChildFragmentManager().findFragmentById(map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setupGoogleMapProperty();
    }

    private void setupGoogleMapProperty() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(LocationUtil.getLastLocation())
                .zoom(16)
                .tilt(60)
                .build();
        googleMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition)
                , 400, null);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onEnableMyLocationButton() {
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Place place = getPresenter().getPlaceByName(marker);
        EventBus.getDefault().post(place);
    }

    @Override
    public void onClearMarker() {
        googleMap.clear();
    }

    @Override
    public void onAddMarker(Place place) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(place.getLat(), place.getLng()))
                .title(place.getName())
                .snippet(place.getAddress()));
    }

    @Override
    public void onLocationChanged(Location location) {
        if (!animate) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(16)
                    .tilt(60)
                    .build();
            if (location.getLatitude() == 0.0 && location.getLongitude() == 0.0) return;
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            animate = true;
        }
    }

    @Override
    public void onAnyPermissionDenied(String message) {
        PermissionManager.showPermissionRequestDeniedDialog(getActivity(), message);
    }
}