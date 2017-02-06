package com.iamwee.placesfinder.view.main;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceViewImpl;


interface MainContractor {
    interface Presenter extends BasePresenterImpl {

        void getCurrentProfile();
    }

    interface View extends ServiceViewImpl<Presenter> {

    }
}
