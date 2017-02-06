package com.iamwee.placesfinder.view.profile;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceViewImpl;


interface ProfileContractor {

    interface Presenter extends BasePresenterImpl {

        void saveProfile(String codeName);

        void cancelCall();
    }

    interface View extends ServiceViewImpl<Presenter> {

        void onProfileSaved(String message);
    }
}
