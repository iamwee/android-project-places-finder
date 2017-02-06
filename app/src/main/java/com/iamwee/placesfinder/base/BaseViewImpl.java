package com.iamwee.placesfinder.base;

/**
 * Created by Zeon on 2/1/2560.
 */

public interface BaseViewImpl<T> {

    void setPresenter(T presenter);

    void onNetworkConnectionFailure();

    void onShowToastMessage(String message);
}
