package com.iamwee.placesfinder.view.profile;

import com.iamwee.placesfinder.BasePresenter;
import com.iamwee.placesfinder.BaseView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface ProfileContractor {

    interface Presenter extends BasePresenter {

        void getCurrentProfile();
    }

    interface View extends BaseView<Presenter> {

    }
}
