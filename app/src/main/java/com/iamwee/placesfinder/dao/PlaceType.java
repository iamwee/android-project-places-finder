package com.iamwee.placesfinder.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zeon on 1/20/17.
 */

public class PlaceType implements Parcelable {

    @SerializedName("id")               private String id;
    @SerializedName("type_name")        private String typeName;

    public PlaceType() {

    }

    protected PlaceType(Parcel in) {
        id = in.readString();
        typeName = in.readString();
    }

    public static final Creator<PlaceType> CREATOR = new Creator<PlaceType>() {
        @Override
        public PlaceType createFromParcel(Parcel in) {
            return new PlaceType(in);
        }

        @Override
        public PlaceType[] newArray(int size) {
            return new PlaceType[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(typeName);
    }
}
