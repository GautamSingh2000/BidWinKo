package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpModel {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("securityToken")
    @Expose
    private String securityToken;
    @SerializedName("socialName")
    @Expose
    private String socialName;
    @SerializedName("socialImgurl")
    @Expose
    private String socialImgurl;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getSocialImgurl() {
        return socialImgurl;
    }

    public void setSocialImgurl(String socialImgurl) {
        this.socialImgurl = socialImgurl;
    }
}