package com.iamwee.placesfinder.view.profile;

import com.iamwee.placesfinder.BasePresenter;
import com.iamwee.placesfinder.BaseView;
import com.iamwee.placesfinder.ServiceView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface ProfileContractor {

    interface Presenter extends BasePresenter {

        void saveProfile(String codeName);

        void cancelCall();
    }

    interface View extends ServiceView<Presenter> {

        void onProfileSaved(String message);
    }
}
