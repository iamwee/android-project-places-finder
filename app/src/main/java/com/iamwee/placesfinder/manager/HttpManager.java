package com.iamwee.placesfinder.manager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zeon on 2/1/2560.
 */

public class HttpManager {

    public static final int BAD_REQUEST = 400;

    private static final String DEV_BASE_URL = "http://10.0.2.2/places_finder_api/public/api/";
    private static final String PRODUCTION_BASE_URL = "http://27.254.63.25/places-finder/public/api/";

    private static Retrofit retrofit;

    public static ApiService getServices() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(DEV_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

}
