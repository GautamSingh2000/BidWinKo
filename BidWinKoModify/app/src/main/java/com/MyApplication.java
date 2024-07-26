package com;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;




public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
       super.attachBaseContext(base);
       MultiDex.install(this);
    }

    /**
     * Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
     */
    public void onCreate() {
        super.onCreate();
        Log.e("MyApplication","openig MyApplication");
    }





}