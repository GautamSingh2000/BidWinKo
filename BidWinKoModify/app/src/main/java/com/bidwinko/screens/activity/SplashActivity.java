package com.bidwinko.screens.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bidwinko.BuildConfig;
import com.bidwinko.MainActivity;
import com.bidwinko.R;
import com.bidwinko.API.APIService;
import com.bidwinko.model.AppOpenModel;
import com.bidwinko.utilies.Constants;
import com.bidwinko.API.Retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {

    ProgressDialog progressDialog;
    String TAG = "Splash Screen" ,tranTxt ="";
    TextView txtVersion;


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("SplashActivity","openig SplashActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("SplashActivity","in OnDestroy  SplashActivity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        txtVersion = findViewById(R.id.txtvesion);
        txtVersion.setText("Version: "+versionName);

        if(isNetworkAvailable(SplashActivity.this)){
            if (Constants.getSharedPreferenceInt(SplashActivity.this, "userId", 0)!=0 &&
                    !Constants.getSharedPreferenceString(SplashActivity.this, "securitytoken", "").equals("null"))
            {
                Log.e("SplashSCreen","openig OpenApp API");
//                openApp();

//                if (getIntent().getExtras() != null) {
//                    String title = null;
//                    String actionk = null;
//
//                    for (String key : getIntent().getExtras().keySet()) {
//                        if (key.equals("OfferKey")) {
//                            actionk = "True";
//                            title = getIntent().getExtras().getString(key);
//                        }
//                    }
//                    if(actionk!=null && actionk.equalsIgnoreCase("True")){
//                        Intent intentProductdDtails = new Intent(SplashActivity.this, ProductDetailsActivity.class);
//                        intentProductdDtails.putExtra("offerId", Integer.parseInt(title));
//                        startActivityForResult(intentProductdDtails,105);
//                    }
//                    else{
//                        openApp();
//                    }
//                }
//                else{
//                    openApp();
//                }

            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!((Activity) SplashActivity.this).isFinishing()){
                            progressDialog = new ProgressDialog(SplashActivity.this);
                            progressDialog.setMessage(getString(R.string.loadingwait));
                            progressDialog.show();
                            progressDialog.setCancelable(false);
                        }


                        Log.e("SplashSCreen","sending to google signin activity !");
                        Intent intent = new Intent(SplashActivity.this, GoogleLoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                },3000);

            }
        }
        else{
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setTitle("Alert!");
            builder.setMessage("Please Check Your Internet Connection.");
            builder.setPositiveButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            builder.setCancelable(false);
            builder.show();
        }


    }

//    public void openApp(){
//        int versionCode = BuildConfig.VERSION_CODE;
//        final String versionName = BuildConfig.VERSION_NAME;
//
//        Constants.setSharedPreferenceInt(SplashActivity.this,"versionCode",versionCode);
//        Constants.setSharedPreferenceString(SplashActivity.this,"versionName",versionName);
//
//        APIService apiService = Retrofit.getClient().create(APIService.class);
//        Call<AppOpenModel> call = apiService.appOpen(Constants.getSharedPreferenceInt(SplashActivity.this,"userId",0),
//                Constants.getSharedPreferenceString(SplashActivity.this,"securitytoken",""),
//                Constants.getSharedPreferenceString(SplashActivity.this,"versionName",""),
//                Constants.getSharedPreferenceInt(SplashActivity.this,"versionCode",0),
//                Constants.getSharedPreferenceString(SplashActivity.this,"userFrom","") );
//
//        if(!((Activity) SplashActivity.this).isFinishing()) {
//            progressDialog = new ProgressDialog(SplashActivity.this);
//            progressDialog.setMessage(getString(R.string.loadingwait));
//            progressDialog.show();
//            progressDialog.setCancelable(false);
//        }
//
//
//        call.enqueue(new Callback<AppOpenModel>() {
//            @Override
//            public void onResponse(Call<AppOpenModel> call, Response<AppOpenModel> response) {
////                progressDialog.dismiss();
//                dismissProgressDialog();
////                Log.e("testing", "onResponse:112121spl "+response.toString() );
//                try {
//                    if(response!=null){
//                        if (response.isSuccessful()) {
////                    Log.e("testing", "onResponse:1forceupdate "+response.body().getForceUpdate() +" status: "+response.body().getStatus() );
//                            if (response.body().getForceUpdate()) {
//                                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
//                                builder.setMessage("Your " + getString(R.string.app_name) + " seems very old, Please update to get new Earning features!!");
//                                builder.setPositiveButton("UPDATE NOW",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog,
//                                                                int which) {
//                                                dialog.dismiss();
//
//                                                try {
//                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
//                                                } catch (ActivityNotFoundException e) {
//                                                    // TODO: handle exception
//                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
//
//                                                }
//                                                finish();
//                                            }
//                                        });
//                                builder.setCancelable(false);
//                                builder.show();
//
//                            } else {
//                                if (response.body().getStatus() == 200) {
//
//                                    Log.e("SplashSCreen","response is 200");
//                                    String amount = String.valueOf(response.body().getAmount());
//                                    String coins = String.valueOf(response.body().getCoin());
//                                    String curency = String.valueOf(response.body().getCurrency());
//                                    int payoutlimit = response.body().getPaycoinLimit();
//                                    String FBurl = response.body().getFbLike();
//                                    String TWurl = response.body().getTwLike();
//                                    String TGMurl = response.body().getTelLike();
//
//                                    String bidremain = response.body().getBidBalance();
//                                    Constants.setSharedPreferenceString(SplashActivity.this,"bidBalance",bidremain);
//
//                                    Constants.setSharedPreferenceInt(SplashActivity.this, "paycoinLimit", payoutlimit);
//                                    Constants.setSharedPreferenceString(SplashActivity.this, "totalamount", amount);
//                                    Constants.setSharedPreferenceString(SplashActivity.this, "totalcoins", coins);
//                                    Constants.setSharedPreferenceString(SplashActivity.this, "curency", curency);
//
//                                    Constants.setSharedPreferenceString(SplashActivity.this, "fburl", FBurl);
//                                    Constants.setSharedPreferenceString(SplashActivity.this, "twurl", TWurl);
//                                    Constants.setSharedPreferenceString(SplashActivity.this, "tgmurl", TGMurl);
//
//                                    tranTxt = response.body().getTransText();
//                                    Constants.setSharedPreferenceString(SplashActivity.this, "tranTxt", tranTxt);
//
//                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                } else {
//
//                                    Log.e("SplashSCreen","response is "+ response.body().getMessage());
//                                    Toast.makeText(SplashActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                        }
//                    }
//                    else Toast.makeText(SplashActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
//
//                }
//                catch (Exception e){
//                    Log.e(TAG, "onResponse:110 "+e );
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AppOpenModel> call, Throwable t) {
//                Toast.makeText(SplashActivity.this,getString(R.string.systemmessage)+t,Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();

    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();

    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==105){
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }




}
