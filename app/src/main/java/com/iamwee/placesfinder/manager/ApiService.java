package com.iamwee.placesfinder.manager;

import com.iamwee.placesfinder.dao.LoginResponse;
import com.iamwee.placesfinder.dao.ServerResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Zeon on 2/1/2560.
 */

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body RequestBody body);

    @POST("auth/logout")
    Call<ServerResponse> logout(@Body RequestBody body);

    @POST("user")
    Call<ServerResponse> createAccount(@Body RequestBody body);
}
