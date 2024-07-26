package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductsDetailsModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private int status;
@SerializedName("bidoffer_details")
@Expose
private BidofferDetails bidofferDetails;

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

public BidofferDetails getBidofferDetails() {
return bidofferDetails;
}

public void setBidofferDetails(BidofferDetails bidofferDetails) {
this.bidofferDetails = bidofferDetails;
}

}