//package com.bidwinko.utilies;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.CountDownTimer;
//import android.os.IBinder;
//import android.util.Log;
//
//public class BroadcastService extends Service {
//
//    private final static String TAG = "BroadcastService";
//
//    public static final String COUNTDOWN_BR = "com.bidwinko.countdown_br";
//    Intent bi = new Intent(COUNTDOWN_BR);
//
//    CountDownTimer cdt = null;
//
//    @Override
//        public void onCreate() {
//            super.onCreate();
//
//            Log.e(TAG, "Starting timer...");
//
//            cdt = new CountDownTimer(30000, 1000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//
//                    Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
//                    bi.putExtra("countdown", millisUntilFinished);
//                    sendBroadcast(bi);
//                }
//
//                @Override
//                public void onFinish() {
//                    Log.e(TAG, "Timer finished");
//                }
//            };
//
//            cdt.start();
//        }
//
//        @Override
//        public void onDestroy() {
//
//            cdt.cancel();
//            Log.e(TAG, "Timer cancelled");
//            super.onDestroy();
//        }
//
//        @Override
//        public int onStartCommand(Intent intent, int flags, int startId) {
//            return super.onStartCommand(intent, flags, startId);
//        }
//
//        @Override
//        public IBinder onBind(Intent arg0) {
//            return null;
//        }
//}