package com.iamwee.placesfinder.view.main;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceView;


interface MainContractor {
    interface Presenter extends BasePresenterImpl {

        void getCurrentProfile();
    }

    interface View extends ServiceView<Presenter> {

    }
}
