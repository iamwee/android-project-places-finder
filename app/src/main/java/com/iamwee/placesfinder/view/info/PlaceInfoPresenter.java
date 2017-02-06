package com.iamwee.placesfinder.view.info;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.iamwee.placesfinder.R;
import com.iamwee.placesfinder.base.BasePresenter;
import com.iamwee.placesfinder.dao.Place;
import com.iamwee.placesfinder.dao.ServerResponse;
import com.iamwee.placesfinder.event.OpenActivity;
import com.iamwee.placesfinder.manager.HttpManager;
import com.iamwee.placesfinder.util.GsonUtil;
import com.iamwee.placesfinder.util.NetworkUtil;
import com.iamwee.placesfinder.util.SessionUtil;
import com.iamwee.placesfinder.view.info.adapter.PlaceInfoConverter;
import com.iamwee.placesfinder.view.info.adapter.model.BasePlaceInfoItem;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class PlaceInfoPresenter extends BasePresenter<PlaceInfoContractor.View>
        implements PlaceInfoContractor.Presenter {

    private Call<ServerResponse> call;

    private PlaceInfoPresenter(PlaceInfoContractor.View view) {
        super(view);
        getView().setPresenter(this);
    }

    static PlaceInfoPresenter newInstance(PlaceInfoContractor.View view) {
        return new PlaceInfoPresenter(view);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        cancelCall();
    }

    @Override
    public void convertToAdapterModel(Place place) {
        List<BasePlaceInfoItem> basePlaceInfoItems = new ArrayList<>();
        PlaceInfoConverter converter = new PlaceInfoConverter();
        basePlaceInfoItems.add(converter.createHeaderSection(place));
        basePlaceInfoItems.add(converter.createTitleSection("About"));
        basePlaceInfoItems.add(converter.createSummarySection(place));
        basePlaceInfoItems.add(converter.createMapSection(new LatLng(place.getLat(), place.getLng())));

        if (place.getImages().size() > 0){
            basePlaceInfoItems.add(converter.createPhotoHeaderSection());
            basePlaceInfoItems.add(converter.createPhotoListSection(
                    place.getImages().subList(0, place.getImages().size() > 5 ? 5 : place.getImages().size())));
        }

        basePlaceInfoItems.addAll(converter.createReviewSection(place.getReviews()));
        getView().onSetAdapter(basePlaceInfoItems);
    }

    @Override
    public void submitPlace(String placeId) {
        RequestBody body = new FormBody.Builder()
                .add("secret", SessionUtil.getSecretCode())
                .add("token", SessionUtil.getToken())
                .add("place_id", placeId)
                .build();
        if (NetworkUtil.isNetworkAvailable()) {
            Call<ServerResponse> call = HttpManager.getServices().submitPlace(body);
            call.enqueue(submitPlaceCallback);
        }
    }

    @Override
    public void getPlaceById(String id) {
        if (NetworkUtil.isNetworkAvailable()) {
            HttpManager.getServices().getPlaceById(
                    id,
                    SessionUtil.getSecretCode(),
                    SessionUtil.getToken()
            ).enqueue(getPlaceByIdCallback);
        } else {
            getView().onRefreshed();
            getView().onNetworkConnectionFailure();
        }
    }

    @Override
    public void uploadImage(String imagePath, Place place) {
        File imageFile = new File(imagePath);
        MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.getName(),
                        RequestBody.create(MEDIA_TYPE_JPG, imageFile))
                .addFormDataPart("place_id", place.getId())
                .addFormDataPart("secret", SessionUtil.getSecretCode())
                .addFormDataPart("token", SessionUtil.getToken())
                .build();
        if (NetworkUtil.isNetworkAvailable()) {
            call = HttpManager.getServices().uploadPhoto(body);
            call.enqueue(uploadCallback);
            getView().onExecuting();
        } else {
            getView().onNetworkConnectionFailure();
        }
    }

    @Override
    public void cancelCall() {
        if (call != null && call.isExecuted()) call.cancel();
    }

    private Callback<ServerResponse> uploadCallback = new Callback<ServerResponse>() {
        @Override
        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
            getView().onPostExecute();
            if (response.isSuccessful()) {
                getView().onShowToastMessage(response.body().getMessage());
            } else if (response.code() == HttpManager.BAD_REQUEST) {
                try {
                    ServerResponse serverResponse = GsonUtil.getInstance()
                            .fromJson(response.errorBody().string(), ServerResponse.class);
                    getView().onShowToastMessage(serverResponse.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ServerResponse> call, Throwable t) {
            getView().onPostExecute();
            t.printStackTrace();
        }
    };

    private Callback<List<Place>> getPlaceByIdCallback = new Callback<List<Place>>() {
        @Override
        public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
            getView().onRefreshed();
            if (response.isSuccessful()) {
                getView().onRefreshed();
                convertToAdapterModel(response.body().get(0));
            } else if (response.code() == HttpManager.BAD_REQUEST) {
                try {
                    ServerResponse serverResponse = GsonUtil.getInstance()
                            .fromJson(response.errorBody().string(), ServerResponse.class);
                    Log.e(getClass().getSimpleName(), serverResponse.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (response.code() == HttpManager.UNAUTHORIZED) {
                getView().onShowToastMessage(getContext().getString(R.string.error_session_expired));
                EventBus.getDefault().post(new OpenActivity(OpenActivity.LOGIN_ACTIVITY));
            }
        }

        @Override
        public void onFailure(Call<List<Place>> call, Throwable t) {
            getView().onRefreshed();
        }
    };

    private Callback<ServerResponse> submitPlaceCallback = new Callback<ServerResponse>() {
        @Override
        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
            getView().onShowToastMessage(response.body().getMessage());
        }

        @Override
        public void onFailure(Call<ServerResponse> call, Throwable t) {
            String error = NetworkUtil.analyzeNetworkException(t);
            if (error != null) getView().onShowToastMessage(error);
        }
    };
}
