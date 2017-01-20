package com.iamwee.placesfinder.view.suggest;

import android.os.Bundle;

import com.iamwee.placesfinder.base.BasePresenter;

/**
 * Created by zeon on 1/20/17.
 */

public class SuggestPlacePresenter extends BasePresenter<SuggestPlaceContractor.View> implements
        SuggestPlaceContractor.Presenter {

    public SuggestPlacePresenter(SuggestPlaceContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    public static SuggestPlacePresenter newInstance(SuggestPlaceContractor.View view) {
        return new SuggestPlacePresenter(view);
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
