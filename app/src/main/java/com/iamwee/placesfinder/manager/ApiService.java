package com.iamwee.placesfinder.manager;

import com.iamwee.placesfinder.dao.LoginResponse;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.dao.PlaceType;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.dao.UserProfile;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body RequestBody body);

    @POST("auth/logout")
    Call<ServerResponse> logout(@Body RequestBody body);

    @POST("auth/register")
    Call<ServerResponse> createAccount(@Body RequestBody body);

    @POST("user/profile")
    Call<UserProfile> getCurrentProfile(@Body RequestBody body);

    @PUT("user/profile/update")
    Call<ServerResponse> updateProfile(@Body RequestBody body);

    @PUT("user/password")
    Call<ServerResponse> changePassword(@Body RequestBody body);

    @GET("place")
    Call<ArrayList<Place>> getAllPlace(@Query("secret") String secret, @Query("token") String token);

    @GET("place/id/{id}")
    Call<List<Place>> getPlaceById(@Path("id") String id, @Query("secret") String secret, @Query("token") String token);

    @POST("place/search")
    Call<List<Place>> searchPlace(@Body RequestBody body);

    @POST("place")
    Call<ServerResponse> suggestPlace(@Body RequestBody body);

    @POST("place/photo")
    Call<ServerResponse> uploadPhoto(@Body RequestBody body);

    @POST("place/type")
    Call<ArrayList<PlaceType>> getPlaceType(@Body RequestBody body);

    @POST("place/review")
    Call<ServerResponse> sendReview(@Body RequestBody body);

    @POST("feedback")
    Call<ServerResponse> sendFeedback(@Body RequestBody body);

    @POST("place/submit")
    Call<ServerResponse> submitPlace(@Body RequestBody body);

    @POST("report")
    Call<ServerResponse> sendReport(@Body RequestBody body);
}
