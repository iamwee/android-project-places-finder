package com.iamwee.placesfinder;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Zeon on 2/1/2560.
 */

public interface BasePresenter {

    void onStart();

    void onStop();

    Bundle onSaveInstanceState();

    void onRestoreInstanceState(Bundle savedState);

    void onResult(int requestCode, int resultCode, Intent data);

}
