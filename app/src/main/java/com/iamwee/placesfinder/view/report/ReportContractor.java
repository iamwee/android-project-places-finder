package com.iamwee.placesfinder.view.report;

import com.iamwee.placesfinder.base.BasePresenterImpl;
import com.iamwee.placesfinder.base.ServiceViewImpl;

interface ReportContractor {

    interface Presenter extends BasePresenterImpl {

        void cancelCall();

        void sendReport(String email, String title, String description);
    }

    interface View extends ServiceViewImpl<Presenter> {

        void onReportSent();
    }

}
