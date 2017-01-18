package com.iamwee.placesfinder.base;

/**
 * Created by Zeon on 4/1/2560.
 */

public interface ServiceView<T> extends BaseView<T> {

    void onServiceExecuting();

    void onServicePostExecute();

}
