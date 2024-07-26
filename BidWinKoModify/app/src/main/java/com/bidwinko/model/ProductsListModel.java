package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductsListModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private int status;
@SerializedName("bidoffers")
@Expose
private ArrayList<Bidoffer> bidoffers = null;

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

public ArrayList<Bidoffer> getBidoffers() {
return bidoffers;
}

public void setBidoffers(ArrayList<Bidoffer> bidoffers) {
this.bidoffers = bidoffers;
}

}
