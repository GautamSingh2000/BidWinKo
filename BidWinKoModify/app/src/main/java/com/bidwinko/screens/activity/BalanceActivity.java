package com.bidwinko.screens.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.bidwinko.R;
import com.bidwinko.API.APIService;

import retrofit2.converter.gson.GsonConverterFactory;

import com.bidwinko.model.GenerateTokenModel;
//import com.bidwinko.uti.IabHelper;
import com.gocashfree.cashfreesdk.CFPaymentService;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_NOTIFY_URL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;


public class BalanceActivity extends AppCompatActivity {

//Toolbar mToolbar;

// For Google Play

    private static final String TAG = "testing";            // Here you can write



    String ITEM_SKU = "",buybidId="",cToken="",notifyUrl="";         // Not used. For testing
    Button mBuy4Button;
//    IabHelper mHelper;
    ProgressDialog progressDialog;

    TextView totalBids,bidAmounts;
    int bidAmount_intent, totalBidAmount = 0;
    String bidTotal_intent;
    AppCompatRadioButton radioButtonWallet,radioButtonGooglePay,radioButtonCashFree;
    String paymode="CashFree";
    LinearLayout payBid;

    boolean walletOption = false,gplayOpt = false,cashFree=false;
    LinearLayout ll_Wallet,ll_GooglePlay,ll_cashfree;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymode_fragment);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        ((AppCompatActivity)this).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">" +getString(R.string.app_name)+" / "+getString(R.string.payment) + "</font>")));


        Intent intent = getIntent();
        if(intent!=null){
            bidAmount_intent = intent.getIntExtra("bidamount", 0);
            bidTotal_intent = intent.getStringExtra("totalbid");
            buybidId = intent.getStringExtra("buybidId");
            walletOption = intent.getBooleanExtra("walletOpt",false);
            gplayOpt = intent.getBooleanExtra("gplayOpt",false);
            cashFree = intent.getBooleanExtra("cashFree",false);
            notifyUrl = intent.getStringExtra("notifyUrl");
            ITEM_SKU = intent.getStringExtra("ITEM_SKU");
        }

        payBid = findViewById(R.id.paybid);
        bidAmounts = findViewById(R.id.bidamount);
        totalBids = findViewById(R.id.bidbalance);
        radioButtonGooglePay = findViewById(R.id.googlepay);
        radioButtonCashFree = findViewById(R.id.cashfree);
        radioButtonWallet = findViewById(R.id.wallet);
        ll_Wallet = findViewById(R.id.ll_radio_wallet);
        ll_GooglePlay = findViewById(R.id.ll_radio_googleplay);
        ll_cashfree = findViewById(R.id.ll_radio_cashfree);


//        bidAmounts.setText(Constants.getSharedPreferenceString(BalanceActivity.this, "curency", "")+bidAmount_intent);
        totalBids.setText(bidTotal_intent);


        if(walletOption){
            ll_Wallet.setVisibility(View.VISIBLE);
        }

        if(gplayOpt){
            ll_GooglePlay.setVisibility(View.VISIBLE);
        }
        if(cashFree){
            ll_cashfree.setVisibility(View.VISIBLE);
        }

        mBuy4Button = (Button) findViewById(R.id.iap4_google_btn);      // For test Google Pay Button

        mBuy4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(paymode.equals("GooglePlay")){
//                    mHelper.launchPurchaseFlow(BalanceActivity.this, ITEM_SKU, 10001,
//                            mPurchaseFinishedListener, "purchasetoken");
//                }
//                else
                    if(paymode.equals("Wallet")){

                }
                else if(paymode.equals("CashFree")){
//                    if(Constants.getSharedPreferenceString(BalanceActivity.this,"mobile","").length() >= 10){
//                        String	customerPhoneCustom = Constants.getSharedPreferenceString(BalanceActivity.this,"mobile","");
//                        generateToken(getString(R.string.order_bidwinko)+"-"+getRandomNumberString(), String.valueOf(bidAmount_intent),notifyUrl,customerPhoneCustom);
//                    }
//                    else{
//                        withEditText();
//                    }

                }
            }
        });

    }



    public void withEditText() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);

        final EditText etMobile = alertLayout.findViewById(R.id.et_mobile);

        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle("Enter Valid Mobile Number")
                // this is set the view from XML inside AlertDialog
                .setView(alertLayout)
                // disallow cancel of AlertDialog on click of back button and outside touch
                .setCancelable(false)
                .setPositiveButton("Submit",null)
                .setNegativeButton("Cancel",null);

        AlertDialog dialog = alert.create();
        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(BalanceActivity.this, "not dissmiss", Toast.LENGTH_SHORT).show();
                String mobile = etMobile.getText().toString();

                if(mobile.isEmpty() || mobile.length() == 0 || mobile.equals("") || mobile == null){
                    Toast.makeText(BalanceActivity.this, "Mobile Number can't Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (mobile.length() < 10) {
                        Toast.makeText(BalanceActivity.this, "Enter Valid Mobile", Toast.LENGTH_SHORT).show();
                    } else {
//                        Constants.setSharedPreferenceString(BalanceActivity.this, "mobile", mobile);
                        dialog.dismiss();
                    }
                }
            }
        });


    }

    public void doPayment(String cToken, String orderId, String orderAmount, String notifyUrl, String customerPhoneCustom) {
        /*
         * token can be generated from your backend by calling cashfree servers. Please
         * check the documentation for details on generating the token.
         * READ THIS TO GENERATE TOKEN: https://bit.ly/2RGV3Pp
         */
//		String token = "TOKEN_DATA";


        /*
         * stage allows you to switch between sandboxed and production servers
         * for CashFree Payment Gateway. The possible values are
         *
         * 1. TEST: Use the Test server. You can use this service while integrating
         *      and testing the CashFree PG. No real money will be deducted from the
         *      cards and bank accounts you use this stage. This mode is thus ideal
         *      for use during the development. You can use the cards provided here
         *      while in this stage: https://docs.cashfree.com/docs/resources/#test-data
         *
         * 2. PROD: Once you have completed the testing and integration and successfully
         *      integrated the CashFree PG, use this value for stage variable. This will
         *      enable live transactions
         */
//		String stage = "TEST";
        String stage = "PROD";

        /*
         * appId will be available to you at CashFree Dashboard. This is a unique
         * identifier for your app. Please replace this appId with your appId.
         * Also, as explained below you will need to change your appId to prod
         * credentials before publishing your app.
         */
//		String appId = "10426e27e26a07ee9dc668aa962401"; //for testing
        String appId = "3293779d9d177fb60fe7d7aa073923"; // for live
        String orderNote = "BDWK Live Order";

//        String customerName = Constants.getSharedPreferenceString(BalanceActivity.this,"displayName","");
//        String customerEmail = Constants.getSharedPreferenceString(BalanceActivity.this,"email","");
        String customerPhone = customerPhoneCustom;
        Map<String, String> params = new HashMap<>();

        params.put(PARAM_APP_ID, appId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
//        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
//        params.put(PARAM_CUSTOMER_EMAIL,customerEmail);
        params.put(PARAM_NOTIFY_URL,notifyUrl);


        for(Map.Entry entry : params.entrySet()) {
            Log.e("testing_CFSKDSample", entry.getKey() + " " + entry.getValue());

        }

        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(BalanceActivity.this,0);
        cfPaymentService.doPayment(BalanceActivity.this, params, cToken, stage, "#f44236", "#FFFFFF");




        // Use the following method for initiating Payments
        // First color - Toolbar background
        // Second color - Toolbar text and back arrow color
    }




    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public void generateToken(String orderId, String orderAmount, String notifyUrl, String customerPhoneCustom){

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://bidwinko.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService api = retrofit.create(APIService.class);

        Map<String, String> mapdata = new HashMap<>();
        mapdata.put("orderAmount", orderAmount);
        mapdata.put("orderId", orderId);

        Call<GenerateTokenModel> call = api.generateToken(mapdata);

        if(!((Activity) BalanceActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(BalanceActivity.this);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<GenerateTokenModel>() {
            @Override
            public void onResponse(Call<GenerateTokenModel> call, Response<GenerateTokenModel> response) {
                dismissProgressDialog();
                if(response!=null){
                    if(response.isSuccessful()){
                        if(response.body().getStatus().equals("OK")){

//							Toast.makeText(BalanceActivity.this, getString(R.string.paymentstatus)+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            cToken = response.body().getCftoken();
                            Log.e(TAG, "onResponse:ctokn "+cToken );
                            doPayment(cToken,orderId,orderAmount,notifyUrl,customerPhoneCustom);

                        }else{
                            Toast.makeText(BalanceActivity.this,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else{
                    Toast.makeText(BalanceActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GenerateTokenModel> call, Throwable t) {
                Log.e("Responsestring", "flai::"+t);
            }
        });



    }




//    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener =
//            new IabHelper.OnIabPurchaseFinishedListener() {
//
//                public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
////
////
////				if(purchase!=null){
////					String purchaseToken = "inapp:" + getPackageName() + ":android.test.purchased";
////					try {
////						int response = mHelper.mService.consumePurchase(3, getPackageName(), purchaseToken);
////					} catch (RemoteException e) {
////						e.printStackTrace();
////					}
////				} else {
////					Log.d(TAG, "Purchase finished:1121 " + result + ", purchase: "+ purchase);
////					String purchaseToken = "inapp:" + getPackageName() + ":android.test.purchased";
////					try {
////						int response = mHelper.mService.consumePurchase(3, getPackageName(), purchaseToken);
////					} catch (RemoteException e) {
////						e.printStackTrace();
////					}
////				}
//                    if (result.isFailure()) {
//
//                        // Handle error
//
//                        return;
//                    }
//
//                    if (purchase.getSku().equals(ITEM_SKU)) {
//                        consumeItem_4();
//
//                    }
//                }
//
//            };





    public void consumeItem_4() {
//        mHelper.queryInventoryAsync(mReceivedInventoryListener_4);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {

            case R.id.wallet:
                if(checked){
                    paymode = "Wallet";
                    radioButtonGooglePay.setChecked(false);
                    radioButtonCashFree.setChecked(false);
                }
                break;

            case R.id.googlepay:
                if (checked) {
                    paymode = "GooglePlay";
                    radioButtonWallet.setChecked(false);
                    radioButtonCashFree.setChecked(false);
                }
                break;


            case R.id.cashfree:
                if(checked){
                    paymode = "CashFree";
                    radioButtonGooglePay.setChecked(false);
                    radioButtonWallet.setChecked(false);
                }

                break;

        }
    }

//    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener_4 =
//            new IabHelper.QueryInventoryFinishedListener() {
//
//                public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
//
//                    if (result.isFailure()) {
//
//                        // Handle failure
//                    } else {
//
//                        mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
//                                mConsumeFinishedListener);
//                    }
//                }
//            };
//
//    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
//            new IabHelper.OnConsumeFinishedListener() {
//
//                public void onConsumeFinished(Purchase purchase, IabResult result) {
//                    Log.e(TAG, "onConsumeFinished: "+purchase +"\n"+result );
//
//
//                    if (result.isSuccess()) {
//
//                        // clickButton.setEnabled(true);
//
//                    } else {
//
//                        // handle error
//                    }
//                }
//            };
//
//

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////	Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
////        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
////            super.onActivityResult(requestCode, resultCode, data);
////            Log.e(TAG, "onActivityResult:data1 "+data );
////            if(requestCode==CFPaymentService.REQ_CODE){
////                if (data != null) {
////                    Bundle bundle = data.getExtras();
////                    String orderId=null,orderAmount = null,signature=null,txTime=null,txStatus=null,txMsg=null,paymentMode=null,referenceId=null;
////                    if (bundle != null)
////                        for (String key : bundle.keySet()) {
////                            if (bundle.getString(key) != null) {
////                                Log.d(TAG, key + " : " + bundle.getString(key));
////                                orderId = bundle.getString("orderId");
////                                orderAmount = bundle.getString("orderAmount");
////                                signature = bundle.getString("signature");
////                                txTime = bundle.getString("txTime");
////                                txStatus = bundle.getString("txStatus");
////                                txMsg = bundle.getString("txMsg");
////                                paymentMode = bundle.getString("paymentMode");
////                                referenceId = bundle.getString("referenceId");
////                            }
////                        }
////                    Log.e(TAG, "onActivityResult: "+orderId +" ordeAmount: "+orderAmount+" sing: "+signature+" time: "+txTime +" status: "+txStatus );
////
////                    if(txStatus.equals("SUCCESS")){
////                        bidPurchase(buybidId, "cashfree", orderId, orderAmount, paymentMode, referenceId, txTime,
////                                txStatus, signature);
////                    }
//////					else{
//////						Toast.makeText(this, txMsg, Toast.LENGTH_SHORT).show();
//////					}
////
////                }
////            }
//
////        }
////        else {
//            if(data!=null) {
//                final String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
//                Log.e(TAG, "onActivityResult:data2 " + purchaseData);
//                try {
//                    JSONObject mainObject = new JSONObject(String.valueOf(data.getExtras().get("INAPP_PURCHASE_DATA")));
//
//                    String orderId = mainObject.getString("orderId");
//                    String packageName = mainObject.getString("packageName");
//                    String productId = mainObject.getString("productId");
//                    String purchaseTime = mainObject.getString("purchaseTime");
//                    String purchaseState = mainObject.getString("purchaseState");
//                    String developerPayload = mainObject.getString("developerPayload");
//                    String purchaseToken = mainObject.getString("purchaseToken");
//                    //for server side api
//                    bidPurchase(buybidId, "googleplay", orderId, productId, packageName, developerPayload, purchaseTime,
//                            purchaseState, purchaseToken);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
////        }
//
//
//
//    }



    @Override
    protected void onStart() {
        super.onStart();

        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmlwwg3RdW7mIXE3OdpeDrDeNq4R+HQfBL0lx4eovf87JBYNEfTqyOpz5O38vDy9H3jw9/rfX93o6+FA+ElkCv35rnZjtvdpoBFxRIpjljC9Xrsuqd26MOvV97L1H4kBYgH1HSXGxp3zTwbky4Tutf9+xFem4LpmvZiHgMgnQROggM14aKNLnxcQgeosk1sJG1VWsOXLxIEAfcwgL92hLerYajoViQYW6tknp984jjN8AfIdszlQKR3676K8Emq6NbtNncT3w2enk7OqXQ5owkgoQ5rCZH0axKfRUGVuPfoY13ZxZKJYooOX5V5/Rvr69y9VuT7zegokXkj38ItWU5QIDAQAB";

//        mHelper = new IabHelper(BalanceActivity.this, base64EncodedPublicKey);
//
//        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
//
//            public void onIabSetupFinished(IabResult result) {
//
//                if (!result.isSuccess()) {
//
//                    Log.d(TAG, "In-app Billing setup failed: " + result);
//
//                } else {
//
//                    Log.d(TAG, "In-app Billing is set up OK");
//
//                    mHelper.enableDebugLogging(true, TAG);
//                }
//            }
//        });
    }

    @Override
    protected void onPause() {
        dismissProgressDialog();
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
//        if (mHelper != null) mHelper.dispose();
//        mHelper = null;

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void bidPurchase(String buybidId,String vendor,String orderId,String productId,String packageName,String developerPayload,String purchaseTime,String purchaseState,String purchaseToken) {

//        APIService apiService = Retrofit.getClient().create(APIService.class);
//        Call<BidPurchaseModel> call = apiService.bidPurchase(Constants.getSharedPreferenceInt(BalanceActivity.this,"userId",0),
//                Constants.getSharedPreferenceString(BalanceActivity.this,"securitytoken",""),
//                Constants.getSharedPreferenceString(BalanceActivity.this,"versionName",""),
//                Constants.getSharedPreferenceInt(BalanceActivity.this,"versionCode",0),buybidId,
//                Constants.getSharedPreferenceString(BalanceActivity.this,"userFrom",""),vendor,orderId,productId,
//                packageName,developerPayload,purchaseTime,purchaseState,purchaseToken);
//
//        if(!((Activity) BalanceActivity.this).isFinishing()) {
//            progressDialog = new ProgressDialog(BalanceActivity.this);
//            progressDialog.setMessage(getString(R.string.loadingwait));
//            progressDialog.show();
//            progressDialog.setCancelable(false);
//        }
//
//        call.enqueue(new Callback<BidPurchaseModel>() {
//            @Override
//            public void onResponse(Call<BidPurchaseModel>call, Response<BidPurchaseModel> response) {
//                dismissProgressDialog();
////				Log.d("Bid", "Number of movies received:size "+response +" bid"+response.body());
//
//                if(response!=null){
//                    if(response.isSuccessful()){
//                        if(response.body().getStatus()==200){
//                            Toast.makeText(BalanceActivity.this, getString(R.string.paymentstatus)+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                            finish();
//
//                        }else{
//                            Toast.makeText(BalanceActivity.this,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }
//                else{
//                    Toast.makeText(BalanceActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<BidPurchaseModel>call, Throwable t) {
//                // Log error here since request failed
//                Log.e("response", t.toString());
//            }
//        });
//

    }


    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }





}
