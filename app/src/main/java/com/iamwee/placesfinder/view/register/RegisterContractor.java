package com.iamwee.placesfinder.view.register;

import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.base.ServiceView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface RegisterContractor {

    interface Presenter extends BasePresenter {

        void createAccount(String email, String password,
                           String confirmPassword,
                           String codeName);

        boolean isEmailValidated(String email);

        boolean isPasswordAndConfirmPasswordMatched(String password, String confirmPassword);

        boolean isPasswordValidated(String password);

        boolean isCodeNameValidated(String codeName);

        void cancelCall();
    }

    interface View extends ServiceView<Presenter> {

        void onCreateAccountSuccess();


    }
}
