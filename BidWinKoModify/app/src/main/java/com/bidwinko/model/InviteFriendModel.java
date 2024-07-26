package com.bidwinko.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InviteFriendModel {




        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("currency")
        @Expose
        private String currency;
        @SerializedName("inviteAmount")
        @Expose
        private String inviteAmount;
        @SerializedName("referralCode")
        @Expose
        private String referralCode;
        @SerializedName("inviteFbUrl")
        @Expose
        private String inviteFbUrl;
        @SerializedName("inviteTextUrl")
        @Expose
        private String inviteTextUrl;
        @SerializedName("inviteImgurl")
        @Expose
        private String inviteImgurl;
        @SerializedName("inviteTextNew")
        @Expose
        private String inviteTextNew;
     /*   @SerializedName("inviteText")
      *//*  @Expose
       // private List<InviteText> inviteText = null;*/

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getInviteAmount() {
            return inviteAmount;
        }

        public void setInviteAmount(String inviteAmount) {
            this.inviteAmount = inviteAmount;
        }

        public String getReferralCode() {
            return referralCode;
        }

        public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
        }

        public String getInviteFbUrl() {
            return inviteFbUrl;
        }

        public void setInviteFbUrl(String inviteFbUrl) {
            this.inviteFbUrl = inviteFbUrl;
        }

        public String getInviteTextUrl() {
            return inviteTextUrl;
        }

        public void setInviteTextUrl(String inviteTextUrl) {
            this.inviteTextUrl = inviteTextUrl;
        }

        public String getInviteImgurl() {
            return inviteImgurl;
        }

        public void setInviteImgurl(String inviteImgurl) {
            this.inviteImgurl = inviteImgurl;
        }

      /*  public List<InviteText> getInviteText() {
            return inviteText;
        }

        public void setInviteText(List<InviteText> inviteText) {
            this.inviteText = inviteText;
        }*/

    public String getInviteTextNew() {
        return inviteTextNew;
    }

    public void setInviteTextNew(String inviteTextNew) {
        this.inviteTextNew = inviteTextNew;
    }
}