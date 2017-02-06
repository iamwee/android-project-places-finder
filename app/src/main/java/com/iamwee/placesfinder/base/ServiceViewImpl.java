package com.iamwee.placesfinder.base;

/**
 * Created by Zeon on 4/1/2560.
 */

public interface ServiceViewImpl<T> extends BaseViewImpl<T> {

    void onExecuting();

    void onPostExecute();

}
