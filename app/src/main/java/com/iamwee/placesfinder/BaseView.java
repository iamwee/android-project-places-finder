package com.iamwee.placesfinder;

/**
 * Created by Zeon on 2/1/2560.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void onNetworkConnectionFailure();

    void onShowToast(String message);
}
