package com.iamwee.placesfinder.view.main;

import com.iamwee.placesfinder.BasePresenter;
import com.iamwee.placesfinder.ServiceView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface MainContractor {
    interface Presenter extends BasePresenter {

    }

    interface View extends ServiceView<Presenter> {

    }
}
