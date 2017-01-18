package com.iamwee.placesfinder.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.iamwee.placesfinder.dao.LoginResponse;
import com.iamwee.placesfinder.dao.UserProfile;

public class SessionUtil {

    private static final String SESSION_PREF = "session";
    private static final String SESSION_PREF_TOKEN = "session_token";
    private static final String SESSION_PREF_SECRET = "session_secret";
    private static final String SESSION_PREF_HAS_LOGGED_IN = "session_has_logged_in";

    private static final String EMAIL_PREF = "email_pref";
    private static final String EMAIL = "email";

    private static final String USER_PREF = "user_pref";
    private static final String USER_PREF_DATA = "user_pref_data";

    public static void createSession(LoginResponse response) {
        SharedPreferences.Editor editor = getSharedPreferenceWithEdit(SESSION_PREF);
        editor.putString(SESSION_PREF_TOKEN, response.getToken());
        editor.putString(SESSION_PREF_SECRET, response.getSecret());
        editor.putBoolean(SESSION_PREF_HAS_LOGGED_IN, true);
        editor.apply();
    }

    public static void destroySession() {
        getSharedPreferenceWithEdit(SESSION_PREF).clear().apply();
    }

    public static void saveEmailThatLoggedIn(String email) {
        getSharedPreferenceWithEdit(EMAIL_PREF).putString(EMAIL, email).apply();
    }

    public static String getEmailThatLoggedIn() {
        return getSharedPreference(EMAIL_PREF).getString(EMAIL, "");
    }

    public static boolean hasLoggedIn() {
        return getSharedPreference(SESSION_PREF).getBoolean(SESSION_PREF_HAS_LOGGED_IN, false);
    }

    public static String getSecretCode() {
        return getSharedPreference(SESSION_PREF).getString(SESSION_PREF_SECRET, "");
    }

    public static String getToken() {
        return getSharedPreference(SESSION_PREF).getString(SESSION_PREF_TOKEN, "");
    }

    public static UserProfile getUserProfile() {
        String json = getSharedPreference(USER_PREF).getString(USER_PREF_DATA, null);
        if (json == null) return new UserProfile();
        return GsonUtil.getInstance().fromJson(json, UserProfile.class);
    }

    public static void saveUserProfile(UserProfile profile) {
        String json = GsonUtil.getInstance().toJson(profile);
        getSharedPreferenceWithEdit(USER_PREF).putString(USER_PREF_DATA, json).apply();
    }

    private static SharedPreferences.Editor getSharedPreferenceWithEdit(String prefName) {
        return getSharedPreference(prefName).edit();
    }

    private static SharedPreferences getSharedPreference(String prefName) {
        return Contextor.getInstance()
                .getContext()
                .getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

}
