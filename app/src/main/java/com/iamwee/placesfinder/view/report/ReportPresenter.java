package com.iamwee.placesfinder.view.report;

import android.os.Bundle;

import com.iamwee.placesfinder.base.BasePresenter;

class ReportPresenter extends BasePresenter<ReportContractor.View>
        implements ReportContractor.Presenter {

    private ReportPresenter(ReportContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    public static ReportPresenter newInstance(ReportContractor.View view){
        return new ReportPresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public Bundle onSaveInstanceState() {
        return null;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {

    }
}
