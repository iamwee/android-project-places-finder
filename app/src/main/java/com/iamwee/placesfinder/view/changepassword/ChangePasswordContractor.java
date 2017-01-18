package com.iamwee.placesfinder.view.changepassword;

import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.base.ServiceView;

/**
 * Created by zeon on 1/18/17.
 */

interface ChangePasswordContractor {

    interface Presenter extends BasePresenter {

        void changePassword(String newPassword, String confirmPassword);

        void cancelCall();
    }

    interface View extends ServiceView<Presenter> {

        void onChangePasswordSuccess(String message);

        void onChangePasswordFailure(String message);
    }
}
