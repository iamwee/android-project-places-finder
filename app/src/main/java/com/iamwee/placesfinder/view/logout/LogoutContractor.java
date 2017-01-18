package com.iamwee.placesfinder.view.logout;

import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.base.BaseView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface LogoutContractor {

    interface Presenter extends BasePresenter {

        void logout();
    }

    interface View extends BaseView<Presenter> {

        void onPostLogout();
    }
}
