package com.iamwee.placesfinder.view.info;

import android.os.Bundle;

import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.view.info.model.BasePlaceInfoItem;

import java.util.ArrayList;
import java.util.List;

class PlaceInfoPresenter extends BasePresenter<PlaceInfoContractor.View>
        implements PlaceInfoContractor.Presenter {

    private PlaceInfoPresenter(PlaceInfoContractor.View view) {
        super(view);
    }

    static PlaceInfoPresenter newInstance(PlaceInfoContractor.View view) {
        return new PlaceInfoPresenter(view);
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

    @Override
    public void convertToAdapterModel(Place place) {
        List<BasePlaceInfoItem> basePlaceInfoItems = new ArrayList<>();
        basePlaceInfoItems.add(PlaceInfoConverter.createSection());
        basePlaceInfoItems.add(PlaceInfoConverter.createSummary());
        basePlaceInfoItems.add(PlaceInfoConverter.createReview());

        getView().onSetAdapter(basePlaceInfoItems);
    }
}
