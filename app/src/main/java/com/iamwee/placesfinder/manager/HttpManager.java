package com.iamwee.placesfinder.manager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zeon on 2/1/2560.
 */

public class HttpManager {

    private static final String DEV_BASE_URL = "http://10.0.2.2/places-finder/public/api/";
    private static final String PRODUCTION_BASE_URL = "http://27.254.63.25/places-finder/public/api/";


    /**
     * HTTP STATUS
     */

    public static final int BAD_REQUEST = 400;

    private static HttpManager instance;

    private Retrofit retrofit;

    private HttpManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(DEV_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static HttpManager getInstance() {
        if (instance == null) instance = new HttpManager();
        return instance;
    }

    public ApiService getServices() {
        return retrofit.create(ApiService.class);
    }

}
