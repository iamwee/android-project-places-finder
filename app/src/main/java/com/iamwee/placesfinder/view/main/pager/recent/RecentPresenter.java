package com.iamwee.placesfinder.view.main.pager.recent;

import android.os.Bundle;

/**
 * Created by Zeon on 2/1/2560.
 */

class RecentPresenter implements RecentContractor.Presenter {

    private RecentContractor.View view;

    private RecentPresenter(RecentContractor.View view){
        this.view = view;
        this.view.setPresenter(this);
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
