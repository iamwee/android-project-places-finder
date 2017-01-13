package com.iamwee.placesfinder;

/**
 * Created by Zeon on 4/1/2560.
 */

public interface ServiceView<T> extends BaseView<T> {

    void onExecuting();

    void onPostExecute();

}
