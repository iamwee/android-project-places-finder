package com.iamwee.placesfinder.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeon on 1/19/17.
 */

public class Place implements Parcelable {

    @SerializedName("id")           private String id;
    @SerializedName("name")         private String name;
    @SerializedName("type")         private String type;
    @SerializedName("lat")          private double lat;
    @SerializedName("lng")          private double lng;
    @SerializedName("address")      private String address;
    @SerializedName("detail")       private String detail;
    @SerializedName("approve")      private int approve;
    @SerializedName("distance")     private float distance;
    @SerializedName("images")       private List<String> images = new ArrayList<>();
    @SerializedName("tags")         private List<String> tags = new ArrayList<>();
    @SerializedName("reviews")      private List<String> reviews = new ArrayList<>();

    public Place() {

    }


    protected Place(Parcel in) {
        id = in.readString();
        name = in.readString();
        type = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        address = in.readString();
        detail = in.readString();
        approve = in.readInt();
        distance = in.readFloat();
        images = in.createStringArrayList();
        tags = in.createStringArrayList();
        reviews = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(address);
        dest.writeString(detail);
        dest.writeInt(approve);
        dest.writeFloat(distance);
        dest.writeStringList(images);
        dest.writeStringList(tags);
        dest.writeStringList(reviews);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }
}
