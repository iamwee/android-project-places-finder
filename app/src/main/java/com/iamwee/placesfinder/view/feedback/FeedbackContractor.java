package com.iamwee.placesfinder.view.feedback;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceViewImpl;


interface FeedbackContractor {

    interface Presenter extends BasePresenterImpl {

        void sendFeedback(String email, String title, String feedback);

        void cancelCall();
    }

    interface View extends ServiceViewImpl<Presenter> {

        void onFeedbackSent(String message);
    }

}
