package com.iamwee.placesfinder.view.main.pager.nearby;

import android.location.Location;

import com.google.android.gms.maps.model.Marker;
import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.BaseView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface PlaceNearbyContractor {

    interface Presenter extends BasePresenterImpl {
        void getPlacesData();
        void getPlaceByName(Marker marker);
        void initLocationServiceClient();
    }

    interface View extends BaseView<Presenter> {
        void onClearMarker();
        void onAddMarker();
        void onLocationChanged(Location location);

        void onAnyPermissionDenied(String message);
    }
}
