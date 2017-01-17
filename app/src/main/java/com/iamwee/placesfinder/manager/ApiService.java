package com.iamwee.placesfinder.manager;

import com.iamwee.placesfinder.dao.LoginResponse;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.dao.UserProfile;

import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Zeon on 2/1/2560.
 */

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
    Call<Objects> getAllPlace();

    @GET("place/{id}")
    Call<Objects> getPlaceById(@Path("id") String id);

    @GET("place/tag/{tag}")
    Call<Objects> getPlaceByTag(@Path("tag") String tag);

    @GET("place/type/{type}")
    Call<Objects> getPlaceByType(@Path("type") String type);

    @POST("place")
    Call<ServerResponse> suggestPlace(@Body RequestBody body);

    @POST("place/photo")
    Call<ServerResponse> addPhoto(@Body RequestBody body);

    @POST("place/type")
    Call<Objects> getPlaceType(@Body RequestBody body);

    @GET("place/review/{placeId}")
    Call<Objects> getReviewByPlaceId(@Path("placeId") String placeId);

    @POST("place/review")
    Call<ServerResponse> sendReview(@Body RequestBody body);

    @POST("feedback")
    Call<ServerResponse> sendFeedback(@Body RequestBody body);

    @GET("report/type")
    Call<Objects> getReportType();

    @POST("report")
    Call<ServerResponse> sendReport(@Body RequestBody body);

    @POST("tag")
    Call<ServerResponse> addTag(@Body RequestBody body);


}
