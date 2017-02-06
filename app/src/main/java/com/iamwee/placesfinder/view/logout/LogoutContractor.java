package com.iamwee.placesfinder.view.logout;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.BaseViewImpl;

/**
 * Created by Zeon on 2/1/2560.
 */

interface LogoutContractor {

    interface Presenter extends BasePresenterImpl {

        void logout();
    }

    interface View extends BaseViewImpl<Presenter> {

        void onPostLogout();
    }
}
