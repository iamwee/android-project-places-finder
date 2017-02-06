package com.iamwee.placesfinder.view.register;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceViewImpl;

/**
 * Created by Zeon on 2/1/2560.
 */

interface RegisterContractor {

    interface Presenter extends BasePresenterImpl {

        void createAccount(String email, String password,
                           String confirmPassword,
                           String codeName);

        boolean isEmailValidated(String email);

        boolean isPasswordAndConfirmPasswordMatched(String password, String confirmPassword);

        boolean isPasswordValidated(String password);

        boolean isCodeNameValidated(String codeName);

        void cancelCall();
    }

    interface View extends ServiceViewImpl<Presenter> {

        void onCreateAccountSuccess();


    }
}
