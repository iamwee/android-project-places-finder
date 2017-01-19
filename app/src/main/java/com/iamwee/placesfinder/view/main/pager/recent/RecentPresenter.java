package com.iamwee.placesfinder.view.main.pager.recent;

import android.os.Bundle;
import com.iamwee.placesfinder.base.BasePresenter;


class RecentPresenter extends BasePresenter<RecentContractor.View>
        implements RecentContractor.Presenter {

    private RecentPresenter(RecentContractor.View view){
        super(view);
        getView().setPresenter(this);
    }

    static RecentPresenter newInstance(RecentContractor.View view){
        return new RecentPresenter(view);
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
