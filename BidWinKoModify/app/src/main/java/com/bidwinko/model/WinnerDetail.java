package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WinnerDetail {

@SerializedName("id")
@Expose
private int id;
@SerializedName("luckyBid")
@Expose
private String luckyBid;
@SerializedName("bidofferName")
@Expose
private String bidofferName;
@SerializedName("bidofferPrice")
@Expose
private String bidofferPrice;
@SerializedName("winnerUser")
@Expose
private String winnerUser;
@SerializedName("winnerLoc")
@Expose
private String winnerLoc;
@SerializedName("offerEndTime")
@Expose
private String offerEndTime;
@SerializedName("offerImageUrl")
@Expose
private String offerImageUrl;
@SerializedName("userImageUrl")
@Expose
private String userImageUrl;

public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public String getLuckyBid() {
return luckyBid;
}

public void setLuckyBid(String luckyBid) {
this.luckyBid = luckyBid;
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

public String getWinnerUser() {
return winnerUser;
}

public void setWinnerUser(String winnerUser) {
this.winnerUser = winnerUser;
}

public String getWinnerLoc() {
return winnerLoc;
}

public void setWinnerLoc(String winnerLoc) {
this.winnerLoc = winnerLoc;
}

public String getOfferEndTime() {
return offerEndTime;
}

public void setOfferEndTime(String offerEndTime) {
this.offerEndTime = offerEndTime;
}

public String getOfferImageUrl() {
return offerImageUrl;
}

public void setOfferImageUrl(String offerImageUrl) {
this.offerImageUrl = offerImageUrl;
}

public String getUserImageUrl() {
return userImageUrl;
}

public void setUserImageUrl(String userImageUrl) {
this.userImageUrl = userImageUrl;
}

}