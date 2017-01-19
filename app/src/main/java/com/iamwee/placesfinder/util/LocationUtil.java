package com.iamwee.placesfinder.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.model.LatLng;
import com.iamwee.placesfinder.R;

public class LocationUtil {

    private static final String LOCATION_PREF = "location.pref";
    private static final String LOCATION_PREF_LAT = "location.pref.lat";
    private static final String LOCATION_PREF_LNG = "location.pref.lng";

    public static boolean isLocationAvailable(Context context) {
        LocationManager manager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void showErrorDialogMessage(final Context context) {
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

    public static void saveCurrentLocation(Location location) {
        SharedPreferences.Editor editor = getLocationSharedPreferencesWithEdit();
        editor.putString(LOCATION_PREF_LAT, String.valueOf(location.getLatitude()));
        editor.putString(LOCATION_PREF_LNG, String.valueOf(location.getLongitude()));
        editor.apply();
    }

    public static LatLng getCurrentLocation() {
        SharedPreferences pref = getLocationSharedPreferences();
        double lat = Double.parseDouble(pref.getString(LOCATION_PREF_LAT, "0.0"));
        double lng = Double.parseDouble(pref.getString(LOCATION_PREF_LNG, "0.0"));
        return new LatLng(lat, lng);
    }

    private static SharedPreferences getLocationSharedPreferences() {
        return Contextor.getInstance().getContext().getSharedPreferences(
                LOCATION_PREF,
                Context.MODE_PRIVATE
        );
    }

    private static SharedPreferences.Editor getLocationSharedPreferencesWithEdit() {
        return getLocationSharedPreferences().edit();
    }
}
