package com.iamwee.placesfinder.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.iamwee.placesfinder.dao.LoginResponse;

/**
 * Created by Zeon on 2/1/2560.
 */

public class SessionUtil {

    private static final String SESSION = "session";
    private static final String SESSION_TOKEN = "session_token";
    private static final String SESSION_SECRET = "session_secret";
    private static final String SESSION_HAS_LOGGED_IN = "session_has_logged_in";

    public static void createSession(LoginResponse response) {
        SharedPreferences.Editor editor = getSharedPreferenceWithEdit(SESSION);
        editor.putString(SESSION_TOKEN, response.getToken());
        editor.putString(SESSION_SECRET, response.getSecret());
        editor.putBoolean(SESSION_HAS_LOGGED_IN, true);
        editor.apply();
    }

    public static void destroySession() {
        getSharedPreferenceWithEdit(SESSION).clear().apply();
    }

    public static boolean hasLoggedIn(){
        return getSharedPreference(SESSION).getBoolean(SESSION_HAS_LOGGED_IN, false);
    }

    public static String getSecretCode(){
        return getSharedPreference(SESSION).getString(SESSION_SECRET, "");
    }

    public static String getToken(){
        return getSharedPreference(SESSION).getString(SESSION_TOKEN, "");
    }

    private static SharedPreferences getSharedPreference(String prefName) {
        return Contextor.getInstance()
                .getContext()
                .getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getSharedPreferenceWithEdit(String prefName){
        return Contextor.getInstance()
                .getContext()
                .getSharedPreferences(prefName, Context.MODE_PRIVATE).edit();
    }

}
