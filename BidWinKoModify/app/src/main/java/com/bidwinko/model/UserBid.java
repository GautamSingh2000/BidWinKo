package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBid {

@SerializedName("bidTime")
@Expose
private String bidTime;
@SerializedName("buyValue")
@Expose
private String buyValue;
@SerializedName("status")
@Expose
private String status;

public String getBidTime() {
return bidTime;
}

public void setBidTime(String bidTime) {
this.bidTime = bidTime;
}

public String getBuyValue() {
return buyValue;
}

public void setBuyValue(String buyValue) {
this.buyValue = buyValue;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}