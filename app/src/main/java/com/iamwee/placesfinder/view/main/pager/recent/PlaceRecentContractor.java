package com.iamwee.placesfinder.view.main.pager.recent;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.BaseViewImpl;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.view.main.pager.recent.adapter.BaseRecentPlaceItem;

import java.util.List;

interface PlaceRecentContractor {

    interface Presenter extends BasePresenterImpl {

        void getPlacesData();

        void convertData(List<Place> places);

        void cancelCall();
    }

    interface View extends BaseViewImpl<Presenter> {
        void onRefreshed();

        void onUpdatePlacesData(List<BaseRecentPlaceItem> items);
    }
}
