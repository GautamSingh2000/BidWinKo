package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bidoffer {

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
@SerializedName("startTime")
@Expose
private String startTime;
@SerializedName("imageUrl")
@Expose
private String imageUrl;

public int getBidofferId() {
return bidofferId;
}

public void setBidofferId(int bidofferId) {
this.bidofferId = bidofferId;
}

public boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
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

}