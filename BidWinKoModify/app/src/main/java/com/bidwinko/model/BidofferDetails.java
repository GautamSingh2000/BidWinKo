package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BidofferDetails {

@SerializedName("bidofferId")
@Expose
private int bidofferId;
@SerializedName("status")
@Expose
private boolean status;
@SerializedName("bidofferName")
@Expose
private String bidofferName;
@SerializedName("bidofferPrice")
@Expose
private String bidofferPrice;
@SerializedName("offerTotalTime")
@Expose
private int offerTotalTime;
@SerializedName("offerTimeLeft")
@Expose
private int offerTimeLeft;
@SerializedName("startTime")
@Expose
private String startTime;
@SerializedName("imageUrl")
@Expose
private String imageUrl;
@SerializedName("offerDescription")
@Expose
private String offerDescription;
@SerializedName("bidBalance")
@Expose
private String bidBalance;



public int getBidofferId() {
return bidofferId;
}

public void setBidofferId(int bidofferId) {
this.bidofferId = bidofferId;
}

public boolean getStatus() {
return status;
}

public void setStatus(boolean status) {
this.status = status;
}

public String getBidofferName() {
return bidofferName;
}

public void setBidofferName(String bidofferName) {
this.bidofferName = bidofferName;
}

public String getBidofferPrice() {
return bidofferPrice;
}

public void setBidofferPrice(String bidofferPrice) {
this.bidofferPrice = bidofferPrice;
}

public int getOfferTotalTime() {
return offerTotalTime;
}

public void setOfferTotalTime(int offerTotalTime) {
this.offerTotalTime = offerTotalTime;
}

public int getOfferTimeLeft() {
return offerTimeLeft;
}

public void setOfferTimeLeft(int offerTimeLeft) {
this.offerTimeLeft = offerTimeLeft;
}

public String getStartTime() {
return startTime;
}

public void setStartTime(String startTime) {
this.startTime = startTime;
}

public String getImageUrl() {
return imageUrl;
}

public void setImageUrl(String imageUrl) {
this.imageUrl = imageUrl;
}

public String getOfferDescription() {
return offerDescription;
}

public void setOfferDescription(String offerDescription) {
this.offerDescription = offerDescription;
}

    public String getBidBalance() {
        return bidBalance;
    }

    public void setBidBalance(String bidBalance) {
        this.bidBalance = bidBalance;
    }
}