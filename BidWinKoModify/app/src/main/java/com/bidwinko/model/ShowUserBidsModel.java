package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShowUserBidsModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private int status;
@SerializedName("user_bids")
@Expose
private ArrayList<UserBid> userBids = null;

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

public ArrayList<UserBid> getUserBids() {
return userBids;
}

public void setUserBids(ArrayList<UserBid> userBids) {
this.userBids = userBids;
}

}