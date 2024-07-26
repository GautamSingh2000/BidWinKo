package com.bidwinko.utilies;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.analytics.CampaignTrackingReceiver;

public class UtmReceiver extends CampaignTrackingReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            Bundle extra = intent.getExtras();
            if(extra!=null) {
                String refer= extra.getString("referrer");
                if(refer!=null){
                    String referrer=refer.substring(0,refer.length());
                    String params[]=referrer.split("&");
                    String utm_source = params[0].substring(params[0].lastIndexOf("=") + 1);
                    String utm_medium = params[1].substring(params[1].lastIndexOf("=") + 1);
                    String utm_term = params[2].substring(params[2].lastIndexOf("=") + 1);
                    String utm_campaign = params[3].substring(params[3].lastIndexOf("=") + 1);
                    String utm_content = params[4].substring(params[4].lastIndexOf("=") + 1);


                    Constants.setSharedPreferenceString(context,"utm_source",utm_source);
                    Constants.setSharedPreferenceString(context,"utm_medium",utm_medium);
                    Constants.setSharedPreferenceString(context,"utm_term",utm_term);
                    Constants.setSharedPreferenceString(context,"utm_content",utm_content);
                    Constants.setSharedPreferenceString(context,"utm_campaign",utm_campaign);
//                    Log.e("testing:::", "onReceive:1 "+Constants.getSharedPreferenceString(context,"utm_source",utm_source) );
//                    Log.e("testing:::", "onReceive:2 "+Constants.getSharedPreferenceString(context,"utm_medium",utm_medium) );

                }
            }
            new CampaignTrackingReceiver().onReceive(context, intent);
 
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void saveUtmValues(Context context,String key,String val){
        try{
            SharedPreferences preference = context.getSharedPreferences("utm_campaign", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(key, val);
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}