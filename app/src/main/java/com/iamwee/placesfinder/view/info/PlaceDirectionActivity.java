package com.iamwee.placesfinder.view.info;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.common.PlacesFinderActivity;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.manager.permission.PermissionManager;
import com.iamwee.placesfinder.manager.permission.PermissionResult;

import java.util.Arrays;

public class PlaceDirectionActivity extends PlacesFinderActivity
        implements OnMapReadyCallback, PermissionManager.PermissionCallback {

    private GoogleMap googleMap;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_direction);
        setupToolbar();
        setupGoogleMap();
        PermissionManager.requestPermission(Arrays.asList(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        ), this);
    }

    protected void setupToolbar(){
        Intent intent = getIntent();
        place = intent.getParcelableExtra("place");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Direction: " + place.getName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("place", place);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) place = savedInstanceState.getParcelable("place");
    }

    private void setupGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        CameraUpdate cameraUpdate =
                CameraUpdateFactory.newCameraPosition(
                        CameraPosition.fromLatLngZoom(new LatLng(place.getLat(), place.getLng()), 17)
                );
        googleMap.animateCamera(cameraUpdate, 400, null);
        setupGoogleMapProperty();
    }

    private void setupGoogleMapProperty() {
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(place.getLat(), place.getLng()))
                .title(place.getName())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                .snippet(place.getAddress()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_directions) {
            gotoMapActivity();
            finish();
            return true;
        } else if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoMapActivity() {
        String q = "geo:<" + place.getLat() + ">,<"
                + place.getLng() + ">?q=<" + place.getLat()
                + ">,<" + place.getLng() + ">(" + place.getName() + ")";
        Uri gmmIntentUri = Uri.parse(q);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_place_direction_menu, menu);
        return true;
    }

    @Override
    public void onPermissionResult(PermissionResult result) {
        if (result.areAllPermissionsGranted() && googleMap != null){
            googleMap.setMyLocationEnabled(true);
        } else if (result.isAnyPermissionPermanentlyDenied()){
            showPermissionDeniedErrorMessage();
        } else {
            showPermissionDeniedErrorMessage();
        }
    }

    private void showPermissionDeniedErrorMessage(){
        PermissionManager.showPermissionRequestDeniedDialog(
                this,
                getString(R.string.error_we_need_to_access_to_location)
        );
    }
}
