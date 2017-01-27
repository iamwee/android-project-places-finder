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

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private PlaceType type;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    @SerializedName("address")
    private String address;

    @SerializedName("detail")
    private String detail;

    @SerializedName("approve")
    private int approve;

    @SerializedName("distance")
    private float distance;

    @SerializedName("images")
    private List<String> images = new ArrayList<>();

    @SerializedName("tags")
    private List<String> tags = new ArrayList<>();

    @SerializedName("reviews")
    private List<Review> reviews = new ArrayList<>();

    public Place() {

    }

    protected Place(Parcel in) {
        id = in.readString();
        name = in.readString();
        type = in.readParcelable(PlaceType.class.getClassLoader());
        lat = in.readDouble();
        lng = in.readDouble();
        address = in.readString();
        detail = in.readString();
        approve = in.readInt();
        distance = in.readFloat();
        images = in.createStringArrayList();
        tags = in.createStringArrayList();
        reviews = in.createTypedArrayList(Review.CREATOR);
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

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeParcelable(type, flags);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(address);
        dest.writeString(detail);
        dest.writeInt(approve);
        dest.writeFloat(distance);
        dest.writeStringList(images);
        dest.writeStringList(tags);
        dest.writeTypedList(reviews);
    }


    public static class Review implements Parcelable {

        @SerializedName("code_name")
        private String codeName;
        @SerializedName("email")
        private String email;
        @SerializedName("review")
        private String review;
        @SerializedName("publish_date")
        private String reviewDate;

        public Review() {
        }

        protected Review(Parcel in) {
            codeName = in.readString();
            email = in.readString();
            review = in.readString();
            reviewDate = in.readString();
        }

        public static final Creator<Review> CREATOR = new Creator<Review>() {
            @Override
            public Review createFromParcel(Parcel in) {
                return new Review(in);
            }

            @Override
            public Review[] newArray(int size) {
                return new Review[size];
            }
        };

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getReviewDate() {
            return reviewDate;
        }

        public void setReviewDate(String reviewDate) {
            this.reviewDate = reviewDate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(codeName);
            dest.writeString(email);
            dest.writeString(review);
            dest.writeString(reviewDate);
        }
    }
}
