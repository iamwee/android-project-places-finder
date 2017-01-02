package com.iamwee.placesfinder.view.main;

import com.iamwee.placesfinder.BasePresenter;
import com.iamwee.placesfinder.BaseView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface MainContractor {
    interface Presenter extends BasePresenter {

        void show();
    }

    interface View extends BaseView<Presenter> {

    }
}
