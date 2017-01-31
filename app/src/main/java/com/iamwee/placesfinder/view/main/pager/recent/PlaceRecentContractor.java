package com.iamwee.placesfinder.view.main.pager.recent;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.BaseView;
import com.iamwee.placesfinder.dao.Place;

import java.util.ArrayList;

interface PlaceRecentContractor {

    interface Presenter extends BasePresenterImpl {

        void getPlacesData();
    }

    interface View extends BaseView<Presenter> {
        void onRefreshed();

        void onUpdatePlacesData(ArrayList<Place> body);
    }
}
