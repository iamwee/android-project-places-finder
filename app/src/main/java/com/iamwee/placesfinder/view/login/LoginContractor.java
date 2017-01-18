package com.iamwee.placesfinder.view.login;

import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.base.ServiceView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface LoginContractor {

    interface Presenter extends BasePresenter {

        void login(String email, String password);

        void cancelCallLogin();
    }

    interface View extends ServiceView<Presenter> {

        void onLoginSuccess();

        void onLoginAsGuest();

        void onLoginFailure(String message);
    }
}
