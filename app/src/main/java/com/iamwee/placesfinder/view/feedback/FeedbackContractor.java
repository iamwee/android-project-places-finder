package com.iamwee.placesfinder.view.feedback;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceView;


interface FeedbackContractor {

    interface Presenter extends BasePresenterImpl {

        void sendFeedback(String email, String title, String feedback);

        void cancelCall();
    }

    interface View extends ServiceView<Presenter> {

        void onFeedbackSent(String message);
    }

}
