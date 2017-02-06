package com.iamwee.placesfinder.view.info;


import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceViewImpl;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.view.info.adapter.model.BasePlaceInfoItem;

import java.util.List;

interface PlaceInfoContractor {

    interface Presenter extends BasePresenterImpl {

        void convertToAdapterModel(Place place);

        void submitPlace(String placeId);

        void getPlaceById(String id);

        void uploadImage(String imagePath, Place place);

        void cancelCall();
    }

    interface View extends ServiceViewImpl<Presenter> {

        void onSetAdapter(List<BasePlaceInfoItem> basePlaceInfoItems);

        void onRefreshed();
    }
}
