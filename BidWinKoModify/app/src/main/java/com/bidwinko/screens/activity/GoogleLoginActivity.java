package com.bidwinko.screens.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.bidwinko.BuildConfig;
import com.bidwinko.MainActivity;
import com.bidwinko.R;
import com.bidwinko.API.APIService;
import com.bidwinko.model.AppOpenModel;
import com.bidwinko.model.SignUpModel;
import com.bidwinko.API.Retrofit;
import com.bidwinko.utilies.Constants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoogleLoginActivity extends Activity implements View.OnClickListener {


    private static final int RC_SIGN_IN = 101;
    String TAG = "GoogleLoginACtivity";
    ProgressDialog progressDialog;
    String tranTxt = "";
    // [END declare_auth]
    private TextView googleLogin, privacypolicy, termcondition;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleLogin = findViewById(R.id.google_login);
        privacypolicy = findViewById(R.id.txt_privacy);
        termcondition = findViewById(R.id.txt_termcondition);
        privacypolicy.setOnClickListener(this);
        termcondition.setOnClickListener(this);
        googleLogin.setOnClickListener(this);

        // Button listeners
        findViewById(R.id.google_login).setOnClickListener(this);
//         [START initialize_auth]
        // Initialize Firebase Auth
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance());
//        mAuth = FirebaseAuth.getInstance();

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("276302943649-ce98hsrs7vl775dch6gjd06o1rplvvu6.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // [END config_signin]
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        getIdThread1();
    }

    public void getIdThread1() {

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());

                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try {
                    advertId = idInfo.getId();
//                    advertisingId = advertId;
                    Constants.setSharedPreferenceString(GoogleLoginActivity.this, "adverId", advertId);
//                    Log.e("id", "getIdThread: "+advertId );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return advertId;
            }

            @Override
            protected void onPostExecute(String advertId) {
//                Toast.makeText(getApplicationContext(), "advg: "+advertId, Toast.LENGTH_SHORT).show();
            }
        };
        task.execute();


    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this,"Signin Complete!!",Toast.LENGTH_SHORT).show();
//            firebaseAuthWithGoogle(account);
            Intent intent = new Intent(GoogleLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            Log.e(TAG, "onActivityResult: " + account.getDisplayName());
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Toast.makeText(this,"Signin Fails!!",Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Google sign in failed", e);

        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        // [START_EXCLUDE silent]
//        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            Log.e(TAG, "onComplete: "+user.getDisplayName());
//                            Log.e(TAG, "onComplete: "+user.getEmail());

//                            Log.e(TAG, "onComplete:pho "+user.getPhotoUrl());
                            String apilevel = String.valueOf(android.os.Build.VERSION.SDK_INT);
                            String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                            String devicename = android.os.Build.MODEL;
                            String version = "";

                            Log.d(TAG, "task success: " + task.isSuccessful());
                            int versioncode = 0;
                            try {
                                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                version = pInfo.versionName;
                                versioncode = pInfo.versionCode;
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }

                            String userFrom = "bidwinko";
                            Constants.setSharedPreferenceString(GoogleLoginActivity.this, "email", acct.getEmail());
                            Constants.setSharedPreferenceString(GoogleLoginActivity.this, "displayName", acct.getDisplayName());
                            Constants.setSharedPreferenceInt(GoogleLoginActivity.this, "versionCode", versioncode);
                            Constants.setSharedPreferenceString(GoogleLoginActivity.this, "versionName", version);
                            Constants.setSharedPreferenceString(GoogleLoginActivity.this, "userFrom", userFrom);

//                            userSignUp(apilevel, android_id, devicename, "google", acct.getId(),
//                                    Constants.getSharedPreferenceString(GoogleLoginActivity.this, "email", ""), acct.getDisplayName(),
//                                    acct.getPhotoUrl(), Constants.getSharedPreferenceString(GoogleLoginActivity.this, "adverId", ""),
//                                    acct);

                        } else {

                        }


                    }
                });
    }


//    private void userSignUp(String apilevel, String android_id, String devicename, String socialtype, String id, String email,
//                            String displayName, Uri photoUrl, String adId, GoogleSignInAccount acct) {
//        // Toast.makeText(this, "user Signup", Toast.LENGTH_SHORT).show();
//        if (!((Activity) GoogleLoginActivity.this).isFinishing()) {
//            progressDialog = new ProgressDialog(GoogleLoginActivity.this);
//            progressDialog.setMessage(getString(R.string.loadingwait));
//            progressDialog.show();
//            progressDialog.setCancelable(false);
//        }
//
//        APIService apiService = Retrofit.getClient().create(APIService.class);
//        Call<SignUpModel> call = apiService.userSignUp(apilevel, android_id, devicename, socialtype, id, email, displayName, photoUrl,
//                adId, Constants.getSharedPreferenceString(GoogleLoginActivity.this, "versionName", ""),
//                Constants.getSharedPreferenceInt(GoogleLoginActivity.this, "versionCode", 0),
//                Constants.getSharedPreferenceString(GoogleLoginActivity.this, "token", ""),
//                //acct.getIdToken(),
//
//                Constants.getSharedPreferenceString(GoogleLoginActivity.this, "utm_source", ""),
//                Constants.getSharedPreferenceString(GoogleLoginActivity.this, "utm_medium", ""),
//                Constants.getSharedPreferenceString(GoogleLoginActivity.this, "utm_term", ""),
//                Constants.getSharedPreferenceString(GoogleLoginActivity.this, "utm_content", ""),
//                Constants.getSharedPreferenceString(GoogleLoginActivity.this, "utm_campaign", ""),
//                Constants.getSharedPreferenceString(GoogleLoginActivity.this, "userFrom", ""), acct.getIdToken());
//        call.enqueue(new Callback<SignUpModel>() {
//            @Override
//            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
//                Log.d(TAG, "onApi: " + response.message());
//
//                SignUpModel data = response.body();
//                if (data != null) {
//                    progressDialog.dismiss();
//                    Constants.setSharedPreferenceInt(GoogleLoginActivity.this, "userId", data.getUserId());
//                    Constants.setSharedPreferenceString(GoogleLoginActivity.this, "securitytoken", data.getSecurityToken());
////                                Log.e(TAG, "onResponse:userId "+userId +" token:: "+scuritytoken );
//                    //Toast.makeText(GoogleLoginActivity.this, ""+scuritytoken, Toast.LENGTH_SHORT).show();
//                    openApp();
//                }
//
//               /* try{
//                    if(response!=null){
//                        if (response.isSuccessful()) {
//                            if (response.body().getStatus()==200) {
//                                String message = response.body().getMessage();
//                                String scuritytoken = response.body().getSecurityToken();
//                                long userId = response.body().getUserId();
//                                String username = response.body().getSocialName();
//                                String userimage = response.body().getSocialImgurl();
//                                Log.d(TAG, "onResponse: "+scuritytoken);
//                                Constants.setSharedPreferenceInt(GoogleLoginActivity.this, "userId", response.body().getUserId());
//                                Constants.setSharedPreferenceString(GoogleLoginActivity.this, "securitytoken", response.body().getSecurityToken());
////                                Log.e(TAG, "onResponse:userId "+userId +" token:: "+scuritytoken );
//                                Toast.makeText(GoogleLoginActivity.this, ""+scuritytoken, Toast.LENGTH_SHORT).show();
//                                openApp();
//
//                            } else {
//                                Toast.makeText(GoogleLoginActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//                            Toast.makeText(GoogleLoginActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
//                        }
//                    }else Toast.makeText(GoogleLoginActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
//
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }*/
//
//
//            }
//
//            @Override
//            public void onFailure(Call<SignUpModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(GoogleLoginActivity.this, getString(R.string.systemmessage) + t, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]


    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null);
                    }
                });
    }


//    public void openApp() {
//        int versionCode = BuildConfig.VERSION_CODE;
//        final String versionName = BuildConfig.VERSION_NAME;
//        Constants.setSharedPreferenceInt(GoogleLoginActivity.this, "versionCode", versionCode);
//        Constants.setSharedPreferenceString(GoogleLoginActivity.this, "versionName", versionName);
//
//        APIService apiService = Retrofit.getClient().create(APIService.class);
//        Call<AppOpenModel> call = apiService.appOpen(Constants.getSharedPreferenceInt(GoogleLoginActivity.this, "userId", 0),
//                Constants.getSharedPreferenceString(GoogleLoginActivity.this, "securitytoken", ""),
//                Constants.getSharedPreferenceString(GoogleLoginActivity.this, "versionName", ""),
//                Constants.getSharedPreferenceInt(GoogleLoginActivity.this, "versionCode", 0),
//                Constants.getSharedPreferenceString(GoogleLoginActivity.this, "userFrom", ""));
//
//
//        call.enqueue(new Callback<AppOpenModel>() {
//            @Override
//            public void onResponse(Call<AppOpenModel> call, Response<AppOpenModel> response) {
//                try {
//                    if (response != null) {
//                        if (response.isSuccessful()) {
//                            if (response.body().getStatus() == 200) {
//                                String amount = String.valueOf(response.body().getAmount());
//                                String coins = String.valueOf(response.body().getCoin());
//                                String curency = String.valueOf(response.body().getCurrency());
//                                int payoutlimit = response.body().getPaycoinLimit();
//                                String FBurl = response.body().getFbLike();
//                                String TWurl = response.body().getTwLike();
//                                String TGMurl = response.body().getTelLike();
//                                Log.d(TAG, "appOpen: " + amount);
//                                String bidremain = response.body().getBidBalance();
//                                Constants.setSharedPreferenceString(GoogleLoginActivity.this, "bidBalance", bidremain);
//
//                                Constants.setSharedPreferenceInt(GoogleLoginActivity.this, "paycoinLimit", payoutlimit);
//                                Constants.setSharedPreferenceString(GoogleLoginActivity.this, "totalamount", amount);
//                                Constants.setSharedPreferenceString(GoogleLoginActivity.this, "totalcoins", coins);
//                                Constants.setSharedPreferenceString(GoogleLoginActivity.this, "curency", curency);
//
//                                Constants.setSharedPreferenceString(GoogleLoginActivity.this, "fburl", FBurl);
//                                Constants.setSharedPreferenceString(GoogleLoginActivity.this, "twurl", TWurl);
//                                Constants.setSharedPreferenceString(GoogleLoginActivity.this, "tgmurl", TGMurl);
//
//                                tranTxt = response.body().getTransText();
//                                Constants.setSharedPreferenceString(GoogleLoginActivity.this, "tranTxt", tranTxt);
//
//                                Intent intent = new Intent(GoogleLoginActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else
//                                Toast.makeText(GoogleLoginActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                        } else {
//                            Toast.makeText(GoogleLoginActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(GoogleLoginActivity.this, getString(R.string.systemmessage) + "Response Not Found.", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<AppOpenModel> call, Throwable t) {
//                Toast.makeText(GoogleLoginActivity.this, getString(R.string.systemmessage) + t, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
//        int i = v.getId();
        switch (v.getId()) {
            case R.id.google_login:
                signIn();
                break;

            case R.id.txt_privacy:
                String url = "https://bidwinzo.app/privacy-policy.html";
                webViewLoad(url, "Privacy Policy");
                break;

            case R.id.txt_termcondition:
                String urlcondition = "https://bidwinzo.app/terms-conditions.html";
                webViewLoad(urlcondition, "Terms Of Services");
                break;

        }

    }

    private void webViewLoad(String url, String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(GoogleLoginActivity.this);
        alert.setTitle(title);

        WebView wv = new WebView(GoogleLoginActivity.this);
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        alert.setView(wv);
        alert.setNegativeButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
