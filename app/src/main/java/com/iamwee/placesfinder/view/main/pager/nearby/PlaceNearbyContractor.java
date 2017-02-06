package com.iamwee.placesfinder.view.main.pager.nearby;

import android.location.Location;

import com.google.android.gms.maps.model.Marker;
import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.BaseViewImpl;
import com.iamwee.placesfinder.dao.Place;


interface PlaceNearbyContractor {

    interface Presenter extends BasePresenterImpl {
        void getPlacesData();

        void getPlacesFromServer();

        Place getPlaceByName(Marker marker);

        void initLocationServiceClient();
    }

    interface View extends BaseViewImpl<Presenter> {
        void onClearMarker();

        void onAddMarker(Place place);

        void onLocationChanged(Location location);

        void onEnableMyLocationButton();

        void onAnyPermissionDenied(String message);
    }
}
