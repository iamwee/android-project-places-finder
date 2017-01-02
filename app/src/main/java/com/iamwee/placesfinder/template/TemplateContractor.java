package com.iamwee.placesfinder.template;

import com.iamwee.placesfinder.BasePresenter;
import com.iamwee.placesfinder.BaseView;

/**
 * Created by Zeon on 2/1/2560.
 */

interface TemplateContractor {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {

    }
}
