package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailsModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("biduserId")
    @Expose
    private Integer biduserId;
    @SerializedName("bidBal")
    @Expose
    private String bidBal;
    @SerializedName("socialName")
    @Expose
    private String socialName;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("socialEmail")
    @Expose
    private String socialEmail;
    @SerializedName("shippingAddress")
    @Expose
    private String shippingAddress;
    @SerializedName("socialImgurl")
    @Expose
    private String socialImgurl;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBiduserId() {
        return biduserId;
    }

    public void setBiduserId(Integer biduserId) {
        this.biduserId = biduserId;
    }

    public String getBidBal() {
        return bidBal;
    }

    public void setBidBal(String bidBal) {
        this.bidBal = bidBal;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSocialEmail() {
        return socialEmail;
    }

    public void setSocialEmail(String socialEmail) {
        this.socialEmail = socialEmail;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getSocialImgurl() {
        return socialImgurl;
    }

    public void setSocialImgurl(String socialImgurl) {
        this.socialImgurl = socialImgurl;
    }
}