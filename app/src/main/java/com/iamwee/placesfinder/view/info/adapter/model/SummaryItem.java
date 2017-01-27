package com.iamwee.placesfinder.view.info.adapter.model;

/**
 * Created by zeon on 1/24/17.
 */

public class SummaryItem extends BasePlaceInfoItem {

    private String category, name, address, detail;

    public SummaryItem() {
        super(PlaceInfoType.SUMMARY_TYPE);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
