package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BuyBid {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private int status;
@SerializedName("bidBal")
@Expose
private int bidBal;
@SerializedName("walletOpt")
@Expose
private boolean walletOpt;

@SerializedName("gplayOpt")
@Expose
private boolean gplayOpt;
@SerializedName("freeBid")
@Expose
private boolean freeBid;
@SerializedName("buyBidValues")
@Expose
private ArrayList<BuyBidValue> buyBidValues = null;

@SerializedName("bidBalance")
@Expose
private String bidBalance;
@SerializedName("cashFree")
@Expose
private boolean cashFree;

@SerializedName("notifyUrl")
@Expose
private String notifyUrl;

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

public int getBidBal() {
return bidBal;
}

public void setBidBal(int bidBal) {
this.bidBal = bidBal;
}

public boolean getWalletOpt() {
return walletOpt;
}

public void setWalletOpt(boolean walletOpt) {
this.walletOpt = walletOpt;
}



public boolean getGplayOpt() {
return gplayOpt;
}

public void setGplayOpt(boolean gplayOpt) {
this.gplayOpt = gplayOpt;
}

public boolean getFreeBid() {
return freeBid;
}

public void setFreeBid(boolean freeBid) {
this.freeBid = freeBid;
}

public ArrayList<BuyBidValue> getBuyBidValues() {
return buyBidValues;
}

public void setBuyBidValues(ArrayList<BuyBidValue> buyBidValues) {
this.buyBidValues = buyBidValues;
}



    public String getBidBalance() {
        return bidBalance;
    }

    public void setBidBalance(String bidBalance) {
        this.bidBalance = bidBalance;
    }

    public boolean isCashFree() {
        return cashFree;
    }

    public void setCashFree(boolean cashFree) {
        this.cashFree = cashFree;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
