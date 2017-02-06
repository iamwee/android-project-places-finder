package com.iamwee.placesfinder.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.iamwee.placesfinder.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class NetworkUtil {

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static String analyzeNetworkException(Throwable t){
        if (t instanceof ConnectException || t instanceof SocketTimeoutException) {
            return getContext().getString(R.string.error_cannot_connect_to_server);
        } else {
            return null;
        }
    }

    private static Context getContext(){
        return Contextor.getInstance().getContext();
    }
}
