package com.iamwee.placesfinder.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.iamwee.placesfinder.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by Zeon on 2/1/2560.
 */

public class NetworkUtil {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static String analyzeNetworkException(Throwable t){
        if (t instanceof ConnectException || t instanceof SocketTimeoutException) {
            return getContext().getString(R.string.msg_cannot_connect_to_server);
        } else {
            return null;
        }
    }

    private static Context getContext(){
        return Contextor.getInstance().getContext();
    }
}
