package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WinnerModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private int status;
@SerializedName("winner_details")
@Expose
private ArrayList<WinnerDetail> winnerDetails = null;

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

public ArrayList<WinnerDetail> getWinnerDetails() {
return winnerDetails;
}

public void setWinnerDetails(ArrayList<WinnerDetail> winnerDetails) {
this.winnerDetails = winnerDetails;
}

}