package com.iamwee.placesfinder.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.iamwee.placesfinder.dao.Place;

import java.util.ArrayList;
import java.util.List;


public class PlaceUtil {

    private static final String PLACE_PREF = "place_pref";
    private static final String PLACE_PREF_DATA = "place_pref_data";

    public static void cache(ArrayList<Place> places) {
        List<Place> newPlaces = places.subList(0, places.size() > 20 ? 20 : places.size());
        String json = GsonUtil.getInstance().toJson(
                newPlaces,
                new TypeToken<ArrayList<Place>>() {
                }.getType()
        );
        getSharedPreferenceWithEdit().putString(PLACE_PREF_DATA, json).apply();
    }

    public static ArrayList<Place> load() {
        return GsonUtil.getInstance().fromJson(
                getSharedPreference().getString(PLACE_PREF_DATA, "[]"),
                new TypeToken<ArrayList<Place>>() {
                }.getType()
        );
    }

    public static void clear() {
        getSharedPreferenceWithEdit().clear().apply();
    }

    private static SharedPreferences getSharedPreference() {
        return Contextor.getInstance().getContext().getSharedPreferences(
                PLACE_PREF,
                Context.MODE_PRIVATE
        );
    }

    private static SharedPreferences.Editor getSharedPreferenceWithEdit() {
        return getSharedPreference().edit();
    }
}
