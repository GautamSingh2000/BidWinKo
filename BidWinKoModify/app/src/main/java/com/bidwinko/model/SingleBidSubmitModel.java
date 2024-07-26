package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleBidSubmitModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private int status;
@SerializedName("displayMsg")
@Expose
private String displayMsg;

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

public String getDisplayMsg() {
return displayMsg;
}

public void setDisplayMsg(String displayMsg) {
this.displayMsg = displayMsg;
}

    public String getBidBalance() {
        return bidBalance;
    }

    public void setBidBalance(String bidBalance) {
        this.bidBalance = bidBalance;
    }
}