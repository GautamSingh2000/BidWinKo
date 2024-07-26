package com.bidwinko.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppOpenModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("forceUpdate")
    @Expose
    private boolean forceUpdate;
    @SerializedName("coin")
    @Expose
    private int coin;
    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("transText")
    @Expose
    private String transText;

    @SerializedName("paycoinLimit")
    @Expose
    private int paycoinLimit;

    @SerializedName("fbLike")
    @Expose
    private String fbLike;

    @SerializedName("twLike")
    @Expose
    private String twLike;

    @SerializedName("telLike")
    @Expose
    private String telLike;

    @SerializedName("bidBalance")
    @Expose
    private String bidBalance;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransText() {
        return transText;
    }

    public void setTransText(String transText) {
        this.transText = transText;
    }

    public int getPaycoinLimit() {
        return paycoinLimit;
    }

    public void setPaycoinLimit(int paycoinLimit) {
        this.paycoinLimit = paycoinLimit;
    }


    public String getFbLike() {
        return fbLike;
    }

    public void setFbLike(String fbLike) {
        this.fbLike = fbLike;
    }

    public String getTwLike() {
        return twLike;
    }

    public void setTwLike(String twLike) {
        this.twLike = twLike;
    }

    public String getTelLike() {
        return telLike;
    }

    public void setTelLike(String telLike) {
        this.telLike = telLike;
    }

    public String getBidBalance() {
        return bidBalance;
    }

    public void setBidBalance(String bidBalance) {
        this.bidBalance = bidBalance;
    }
}