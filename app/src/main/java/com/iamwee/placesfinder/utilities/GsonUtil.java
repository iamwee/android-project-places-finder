package com.iamwee.placesfinder.utilities;

import com.google.gson.Gson;

/**
 * Created by zeon on 1/17/17.
 */

public class GsonUtil {

    private static Gson gson;

    public static Gson getInstance() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }
}
