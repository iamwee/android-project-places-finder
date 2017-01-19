package com.iamwee.placesfinder.view.profile;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface ProfileContractor {

    interface Presenter extends BasePresenterImpl {

        void saveProfile(String codeName);

        void cancelCall();
    }

    interface View extends ServiceView<Presenter> {

        void onProfileSaved(String message);
    }
}
