package com.iamwee.placesfinder.common;

import android.support.v4.app.Fragment;
import android.view.View;

import com.iamwee.placesfinder.base.BasePresenterImpl;

/**
 * Created by Zeon on 2/1/2560.
 */

public class PlacesFinderFragment<CP extends BasePresenterImpl>
        extends Fragment {

    private CP presenter;

    public CP getPresenter() {
        return this.presenter;
    }

    public void setPresenter(CP presenter) {
        this.presenter = presenter;
    }

    protected void initView(View rootView) {
    }

    protected void setupView(View rootView) {
    }


}
