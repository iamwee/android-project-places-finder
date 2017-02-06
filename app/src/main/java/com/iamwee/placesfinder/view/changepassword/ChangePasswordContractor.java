package com.iamwee.placesfinder.view.changepassword;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceViewImpl;

/**
 * Created by zeon on 1/18/17.
 */

interface ChangePasswordContractor {

    interface Presenter extends BasePresenterImpl {

        void changePassword(String newPassword, String confirmPassword);

        void cancelCall();
    }

    interface View extends ServiceViewImpl<Presenter> {
        void onPasswordChanged(String message);
    }
}
