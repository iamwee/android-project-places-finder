package com.iamwee.placesfinder.view.main;

import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.base.ServiceView;


interface MainContractor {
    interface Presenter extends BasePresenter {

        void getCurrentProfile();
    }

    interface View extends ServiceView<Presenter> {

    }
}
