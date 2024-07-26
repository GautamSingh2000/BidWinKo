package com.bidwinko.utilies;

import android.content.Context;
import android.content.SharedPreferences;


public class Constants {
    private final static String PREF_FILE = "com.bidwinko";
//    public static final String USER_ID = "userid";
//    public static final String USER_NAME = "username";
//    public static final String USER_TYPE = "usertype";
//    public static final String USER_STATE = "userstate";


    public static void setSharedPreferenceString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }




    public static void setSharedPreferenceInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setSharedPreferenceLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void setSharedPreferenceFloat(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.apply();
    }


    static void setSharedPreferenceBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }




    public static String getSharedPreferenceString(Context context, String key, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getString(key, defValue);
    }


    public static int getSharedPreferenceInt(Context context, String key, int defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getInt(key, defValue);
    }
    public static long getSharedPreferenceLong(Context context, String key, long defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getLong(key, defValue);
    }

    public static float getSharedPreferenceFloat(Context context, String key, float defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getFloat(key, defValue);
    }

    public static boolean getSharedPreferenceBoolean(Context context, String key, boolean defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getBoolean(key, defValue);
    }


    public static void logout(Context ctx) {
		SharedPreferences settings;
		SharedPreferences.Editor editor;
		settings = ctx.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
		editor = settings.edit();

		editor.clear();
		editor.commit();
    }


}