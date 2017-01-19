package com.iamwee.placesfinder.common;

import android.support.v4.app.Fragment;
import android.view.View;

import com.iamwee.placesfinder.base.BasePresenterImpl;

/**
 * Created by Zeon on 2/1/2560.
 */

public class PlacesFinderFragment<T extends BasePresenterImpl>
        extends Fragment {

    private T presenter;

    public T getPresenter() {
        return this.presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    protected void initView(View rootView) {
    }

    protected void setupView(View rootView) {
    }


}
