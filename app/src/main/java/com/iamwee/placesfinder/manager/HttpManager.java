package com.iamwee.placesfinder.manager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {

    public static final int BAD_REQUEST = 400;

    public static final String IMAGE_BASE_URL = "http://10.0.2.2/places_finder_api/public/";
    private static final String BASE_URL = "http://10.0.2.2/places_finder_api/public/api/";
    //private static final String BASE_URL = "http://45.76.160.187/places_finder/public/api/";

    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;

    private static Retrofit retrofit;

    public static ApiService getServices() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

}
