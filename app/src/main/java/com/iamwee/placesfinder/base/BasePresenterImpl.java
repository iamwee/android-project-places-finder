package com.iamwee.placesfinder.base;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Zeon on 2/1/2560.
 */

public interface BasePresenterImpl {

    void onStart();

    void onStop();

    Bundle onSaveInstanceState();

    void onRestoreInstanceState(Bundle savedState);

}
