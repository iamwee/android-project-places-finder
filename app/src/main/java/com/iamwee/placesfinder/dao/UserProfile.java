package com.iamwee.placesfinder.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zeon on 1/17/17.
 */

public class UserProfile {

    @SerializedName("email")            private String email;
    @SerializedName("code_name")        private String codeName;

    public UserProfile() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
