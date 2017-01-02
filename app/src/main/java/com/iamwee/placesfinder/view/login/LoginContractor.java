package com.iamwee.placesfinder.view.login;

import com.iamwee.placesfinder.BasePresenter;
import com.iamwee.placesfinder.BaseView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface LoginContractor {

    interface Presenter extends BasePresenter {

        void login(String email, String password);

        void cancelCallLogin();
    }

    interface View extends BaseView<Presenter> {

        void onShowProgressDialog();

        void onDismissProgressDialog();

        void onLoginSuccess();

        void onLoginFailure(String message);
    }
}
