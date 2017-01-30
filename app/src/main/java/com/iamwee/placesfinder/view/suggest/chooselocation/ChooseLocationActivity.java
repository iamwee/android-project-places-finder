package com.iamwee.placesfinder.view.suggest.chooselocation;

import android.Manifest;
import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.manager.permission.PermissionManager;
import com.iamwee.placesfinder.manager.permission.PermissionResult;
import com.iamwee.placesfinder.util.GeoCoderUtil;
import com.iamwee.placesfinder.util.LocationUtil;
import com.iamwee.placesfinder.util.ProgressDialogHelper;

import java.util.Arrays;

public class ChooseLocationActivity extends PlacesFinderActivity
        implements OnMapReadyCallback, PermissionManager.PermissionCallback,
        View.OnClickListener, GeoCoderUtil.Callback {

    private GoogleMap googleMap;
    private GeoCoderUtil geoCoderUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        setupToolbar();
        setupView();
        setupGoogleMap();

    }

    @Override
    protected void setupToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void setupView() {
        findViewById(R.id.iv_marker).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setupGoogleMapProperty();
        PermissionManager.requestPermission(Arrays.asList(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        ), this);
    }

    private void setupGoogleMapProperty() {
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newCameraPosition(
                        CameraPosition.fromLatLngZoom(LocationUtil.getLastLocation(), 17)
                );
        googleMap.animateCamera(cameraUpdate, 400, null);
    }

    @Override
    public void onPermissionResult(PermissionResult permissionResult) {
        if (permissionResult.areAllPermissionsGranted()) {
            if (googleMap != null) googleMap.setMyLocationEnabled(true);
        } else if (permissionResult.isAnyPermissionPermanentlyDenied()) {
            PermissionManager.showPermissionRequestDeniedDialog(
                    this,
                    getString(R.string.error_we_need_to_access_to_location)
            );
        } else {
            PermissionManager.showPermissionRequestDeniedDialog(
                    this,
                    getString(R.string.error_we_need_to_access_to_location)
            );
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_marker) {
            if (geoCoderUtil == null)
                geoCoderUtil = new GeoCoderUtil.Builder()
                        .withLocation(googleMap.getCameraPosition().target)
                        .build();
            ProgressDialogHelper.show(this);
            geoCoderUtil.findAddress(this);
        }
    }

    @Override
    public void onFindAddressResult(Address address, String addressName) {
        ProgressDialogHelper.dismiss();
        LatLng latLng = googleMap.getCameraPosition().target;
        Intent intent = new Intent();
        intent.putExtra("address", addressName);
        intent.putExtra("lat", latLng.latitude);
        intent.putExtra("lng", latLng.longitude);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onFindAddressFailure(Throwable t) {
        ProgressDialogHelper.dismiss();
        if (t instanceof IllegalStateException) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (t instanceof NetworkErrorException) {
            Toast.makeText(this, R.string.error_check_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            t.printStackTrace();
        }
    }

}
