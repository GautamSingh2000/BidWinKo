package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyBidValue {

    @SerializedName("buybidId")
    @Expose
    private String buybidId;
    @SerializedName("buybidName")
    @Expose
    private String buybidName;
    @SerializedName("buybidPrice")
    @Expose
    private int buybidPrice;
    @SerializedName("inAppId")
    @Expose
    private String inAppId;
    @SerializedName("validity")
    @Expose
    private String validity;

    public String getBuybidId() {
        return buybidId;
    }

    public void setBuybidId(String buybidId) {
        this.buybidId = buybidId;
    }

    public String getBuybidName() {
        return buybidName;
    }

    public void setBuybidName(String buybidName) {
        this.buybidName = buybidName;
    }

    public int getBuybidPrice() {
        return buybidPrice;
    }

    public void setBuybidPrice(int buybidPrice) {
        this.buybidPrice = buybidPrice;
    }

    public String getInAppId() {
        return inAppId;
    }

    public void setInAppId(String inAppId) {
        this.inAppId = inAppId;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

}