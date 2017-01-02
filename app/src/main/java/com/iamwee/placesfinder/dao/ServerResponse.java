package com.iamwee.placesfinder.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zeon on 2/1/2560.
 */

public class ServerResponse {

    @SerializedName("message")
    private String message;

    public ServerResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
