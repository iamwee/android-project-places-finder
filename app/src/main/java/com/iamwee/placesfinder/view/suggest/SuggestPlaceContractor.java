package com.iamwee.placesfinder.view.suggest;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.BaseView;
import com.iamwee.placesfinder.base.ServiceView;
import com.iamwee.placesfinder.dao.PlaceType;

import java.util.List;

/**
 * Created by zeon on 1/20/17.
 */

interface SuggestPlaceContractor {
    interface Presenter extends BasePresenterImpl {

        void getTypeOfPlace();

        void cancelCall();

        void submitPlace(String name,
                         LatLng latLng,
                         String typeText,
                         String address,
                         String detail);

        Bundle onSaveInstanceState();

        void onRestoreInstanceState(Bundle savedState);
    }

    interface View extends ServiceView<Presenter> {

        void onPostGetPlaceType(List<PlaceType> placeTypes);

        void onPlaceSubmitted();
    }
}
