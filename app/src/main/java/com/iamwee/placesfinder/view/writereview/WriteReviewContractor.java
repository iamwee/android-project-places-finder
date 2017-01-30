package com.iamwee.placesfinder.view.writereview;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceView;



interface WriteReviewContractor {

    interface Presenter extends BasePresenterImpl {

        void submitReview(String placeId, String reviewMessage);

        void cancelCall();
    }

    interface View extends ServiceView<Presenter> {

        void onReviewSubmitted(String message);
    }

}
