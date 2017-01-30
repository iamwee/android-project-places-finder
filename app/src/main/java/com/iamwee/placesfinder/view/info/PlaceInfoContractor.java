package com.iamwee.placesfinder.view.info;


import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.BaseView;
import com.iamwee.placesfinder.base.ServiceView;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.view.info.adapter.model.BasePlaceInfoItem;

import java.util.List;

interface PlaceInfoContractor {
    interface Presenter extends BasePresenterImpl {

        void convertToAdapterModel(Place place);

        void submitPlace(String placeId);
    }

    interface View extends BaseView<Presenter> {

        void onSetAdapter(List<BasePlaceInfoItem> basePlaceInfoItems);
    }
}
