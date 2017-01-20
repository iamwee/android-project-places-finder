package com.iamwee.placesfinder.util;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GeoCoderUtil {

    private LatLng latLng;
    private Callback callback;

    private GeoCoderUtil(Builder builder) {
        this.latLng = builder.latLng;
    }

    public void findAddress(final Callback c) {
        this.callback = c;
        if (latLng == null) {
            Throwable throwable = new Throwable(
                    "You must set your location before find address name.",
                    new IllegalStateException().getCause()
            );
            c.onFindAddressFailure(throwable);
            return;
        }

        Log.d(getClass().getSimpleName(), "findAddress() called with: callback = [" + c + "]");

        //TODO: rxJava just call once, fix it.

        Observable.fromCallable(
                new Callable<Address>() {
                    @Override
                    public Address call() throws Exception {
                        return getAddressFromLocation();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Address>() {
                    @Override
                    public void onCompleted() {
                        Log.i(getClass().getSimpleName(), "onComplete: success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Address address) {
                        Log.d(getClass().getSimpleName(), "onNext: " + GsonUtil.getInstance().toJson(address));
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            stringBuilder.append(address.getAddressLine(i));
                            if (i != address.getMaxAddressLineIndex()) stringBuilder.append(", ");
                        }

                        if (callback != null)
                            callback.onFindAddressResult(address, stringBuilder.toString());
                    }
                });
    }

    private Address getAddressFromLocation() {
        Address address;
        Geocoder geocoder = new Geocoder(Contextor.getInstance().getContext());
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1
            );
            address = addressList.get(0);
        } catch (IOException e) {
            if (callback != null)
                callback.onFindAddressFailure(new Throwable(e.getMessage(), e.getCause()));
            return null;
        }
        return address;
    }

    public static class Builder {

        private LatLng latLng;

        public GeoCoderUtil.Builder withLocation(LatLng latLng) {
            this.latLng = latLng;
            return this;
        }

        public GeoCoderUtil build() {
            return new GeoCoderUtil(this);
        }
    }

    public interface Callback {
        void onFindAddressResult(Address address, String addressName);

        void onFindAddressFailure(Throwable t);
    }

}
