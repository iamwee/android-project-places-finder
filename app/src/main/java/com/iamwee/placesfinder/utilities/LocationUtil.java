package com.iamwee.placesfinder.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.iamwee.placesfinder.R;

/**
 * Created by Zeon on 2/1/2560.
 */

public class LocationUtil {

    public static boolean isLocationAvailable(Context context) {
        LocationManager manager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void showError(final Context context) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.msg_location_disable_error)
                .setPositiveButton(R.string.action_enable, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.
                                ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}
