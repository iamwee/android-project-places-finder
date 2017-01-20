package com.iamwee.placesfinder.util;

import android.accounts.NetworkErrorException;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoCoderUtil {

    private LatLng latLng;
    private Callback callback;

    private GeoCoderUtil(Builder builder) {
        this.latLng = builder.latLng;
    }

    public void findAddress(final Callback c) {
        this.callback = c;
        if (latLng == null) {
            c.onFindAddressFailure(new IllegalStateException("You must set your location before find address."));
            return;
        }
        new GetAddressTask().execute(latLng);
    }

    private class GetAddressTask extends AsyncTask<LatLng, Void, Address> {

        @Override
        protected Address doInBackground(LatLng... params) {
            Address address;
            Geocoder geocoder = new Geocoder(Contextor.getInstance().getContext(), Locale.ENGLISH);
            try {
                List<Address> addressList = geocoder.getFromLocation(
                        latLng.latitude,
                        latLng.longitude,
                        1
                );
                address = addressList.get(0);
            } catch (IOException e) {
                if (callback != null)
                    callback.onFindAddressFailure(new IOException(e.getMessage()));
                return null;
            }
            return address;
        }

        @Override
        protected void onPostExecute(Address address) {
            super.onPostExecute(address);
            if (address == null) {
                callback.onFindAddressFailure(new NetworkErrorException("You must connect to the internet before find address."));
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                stringBuilder.append(address.getAddressLine(i));
                if (i != address.getMaxAddressLineIndex()) stringBuilder.append(", ");
            }

            if (callback != null)
                callback.onFindAddressResult(address, stringBuilder.toString());
        }
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
