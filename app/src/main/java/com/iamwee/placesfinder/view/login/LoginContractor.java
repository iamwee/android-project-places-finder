package com.iamwee.placesfinder.view.login;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceViewImpl;


interface LoginContractor {

    interface Presenter extends BasePresenterImpl {

        void login(String email, String password);

        void cancelCallLogin();
    }

    interface View extends ServiceViewImpl<Presenter> {

        void onLoginSuccess();

        void onLoginAsGuest();

        void onLoginFailure(String message);
    }
}
