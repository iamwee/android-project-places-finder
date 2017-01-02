package com.iamwee.placesfinder.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zeon on 2/1/2560.
 */

public class LoginResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("secret")
    private String secret;

    public LoginResponse() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
