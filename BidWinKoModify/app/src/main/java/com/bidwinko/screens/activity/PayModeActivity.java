//package com.bidwinko.activity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.RemoteException;
//import android.text.Html;
//import android.util.Log;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatRadioButton;
//
//
//import com.bidwinko.R;
//import com.bidwinko.utilies.Constants;
//
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//public class PayModeActivity extends AppCompatActivity {
//
//    TextView totalBids,bidAmounts;
//    int bidAmount_intent, totalBidAmount = 0;
//    String bidTotal_intent;
//    AppCompatRadioButton radioButtonWallet,radioButtonPaytm,radioButtonGooglePay,radioButtonRazorPay;
//    String paymode="GooglePlay",TAG="testing";
//    LinearLayout payBid;
//
//    //for app googleplay
//     IabHelper mHelper;
//
//    String ITEM_SKU = "android.test.purchased";
////    String ITEM_SKU = "android.test.canceled";
////    String ITEM_SKU = "android.test.item_unavailable";
//    boolean walletOption = false,paytmOpt = false,razorPay = false,gplayOpt = false;
//    LinearLayout ll_Wallet,ll_Paytm,ll_RazorPay,ll_GooglePlay;
//
//    ProgressDialog progressDialog;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.paymode_fragment);
//
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
//        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
//        ((AppCompatActivity)this).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">" +getString(R.string.app_name)+" / "+getString(R.string.payment) + "</font>")));
//
//
//        Intent intent = getIntent();
//        if(intent!=null){
//            bidAmount_intent = intent.getIntExtra("bidamount", 0);
//            bidTotal_intent = intent.getStringExtra("totalbid");
//            walletOption = intent.getBooleanExtra("walletOpt",false);
//            paytmOpt = intent.getBooleanExtra("paytmOpt",false);
//            razorPay = intent.getBooleanExtra("razorPay",false);
//            gplayOpt = intent.getBooleanExtra("gplayOpt",false);
////            ITEM_SKU = intent.getStringExtra("ITEM_SKU");
//            Log.e(TAG, "onCreate:ITEM_SKU "+ITEM_SKU );
//        }
//
//
//
//        payBid = findViewById(R.id.paybid);
//        bidAmounts = findViewById(R.id.bidamount);
//        totalBids = findViewById(R.id.bidbalance);
//        radioButtonPaytm = findViewById(R.id.paytm);
//        radioButtonGooglePay = findViewById(R.id.googlepay);
//        radioButtonRazorPay = findViewById(R.id.razorpay);
//        radioButtonWallet = findViewById(R.id.wallet);
//        ll_Wallet = findViewById(R.id.ll_radio_wallet);
//        ll_Paytm = findViewById(R.id.ll_radio_paytm);
//        ll_GooglePlay = findViewById(R.id.ll_radio_googleplay);
//        ll_RazorPay = findViewById(R.id.ll_radio_razorpay);
//
//
//        bidAmounts.setText(Constants.getSharedPreferenceString(PayModeActivity.this, "curency", "")+bidAmount_intent);
//        totalBids.setText(bidTotal_intent);
//
//
//        if(walletOption){
//            ll_Wallet.setVisibility(View.VISIBLE);
//        }
//        if(paytmOpt){
//            ll_Paytm.setVisibility(View.VISIBLE);
//        }
//        if(razorPay){
//            ll_RazorPay.setVisibility(View.VISIBLE);
//        }
//        if(gplayOpt){
//            ll_GooglePlay.setVisibility(View.VISIBLE);
//        }
//
//
//
//
//         payBid.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//
//                 if(paymode.equals("GooglePlay")){
//                     mHelper.launchPurchaseFlow(PayModeActivity.this, ITEM_SKU, 10001,
//                             mPurchaseFinishedListener, "purchasetoken");
//                 }
//                 else if(paymode.equals("wallet")){
//
//                 }
//
//
//
//             }
//         });
//
//    }
//
//
////    private String initOrderId() {
////        Random r = new Random(System.currentTimeMillis());
////        String orderId = "ORDER" + (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);
////        return orderId;
////    }
////
////    private String initCustomerId() {
////        Random r = new Random(System.currentTimeMillis());
////        String orderId = "CUST" + (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);
////        return orderId;
////    }
//
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent data) {
//        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }else{
//             try {
//                JSONObject mainObject = new JSONObject(String.valueOf(data.getExtras().get("INAPP_PURCHASE_DATA")));
////                bidPurchase(ITEM_SKU,"vendor",mainObject.getString("orderId"),
////                        mainObject.getString("productId"),mainObject.getString("transactionId"),mainObject.getString("developerPayload"),
////                        mainObject.getString("purchaseTime"),mainObject.getString("purchaseState"),mainObject.getString("acknowledged"),
////                        mainObject.getString("packageName"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener =
//            new IabHelper.OnIabPurchaseFinishedListener() {
//
//                public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
//
//
//                    if(purchase!=null){
//                        String purchaseToken = "inapp:" + getPackageName() + ":android.test.purchased";
//                        try {
//                            int response = mHelper.mService.consumePurchase(3, getPackageName(), purchaseToken);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Log.d(TAG, "Purchase finished:1121 " + result + ", purchase: "+ purchase);
//                        String purchaseToken = "inapp:" + getPackageName() + ":android.test.purchased";
//                        try {
//                            int response = mHelper.mService.consumePurchase(3, getPackageName(), purchaseToken);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    }
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
//
//    public void consumeItem_4() {
//        mHelper.queryInventoryAsync(mReceivedInventoryListener_4);
//
//    }
//
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
////    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener  = new IabHelper.OnIabPurchaseFinishedListener() {
////        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
////
////            if(purchase!=null){
////               String purchaseToken = "inapp:" + getPackageName() + ":android.test.purchased";
////               try {
////                   int response = mHelper.mService.consumePurchase(3, getPackageName(), purchaseToken);
////               } catch (RemoteException e) {
////                   e.printStackTrace();
////               }
////           } else {
////                Log.d(TAG, "Purchase finished:1121 " + result + ", purchase: "+ purchase);
////                String purchaseToken = "inapp:" + getPackageName() + ":android.test.purchased";
////                try {
////                    int response = mHelper.mService.consumePurchase(3, getPackageName(), purchaseToken);
////                } catch (RemoteException e) {
////                    e.printStackTrace();
////                }
////            }
////
////            if (result.isFailure()) {
////                // Handle error
////                return;
////            }
////            else if (purchase.getSku().equals(ITEM_SKU)) {
////                consumeItem();
////
////            }
////
////        }
////    };
////
////    public void consumeItem() {
////        mHelper.queryInventoryAsync(mReceivedInventoryListener);
////    }
////
////    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener   = new IabHelper.QueryInventoryFinishedListener() {
////        public void onQueryInventoryFinished(IabResult result,
////                                             Inventory inventory) {
////
////
////            if (result.isFailure()) {
////                // Handle failure
////            } else {
////                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU), mConsumeFinishedListener);
////            }
////        }
////    };
////
////    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =  new IabHelper.OnConsumeFinishedListener() {
////                public void onConsumeFinished(Purchase purchase,
////                                              IabResult result) {
////
////                    if (result.isSuccess()) {
////                        Log.e(TAG, "onConsumeFinished:Res "+result.toString());
////                        Log.e(TAG, "onConsumeFinished:ult "+result);
////
////                    } else {
////                        // handle error
////                    }
////                }
////            };
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
////        String base64EncodedPublicKey = "";
//	String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmlwwg3RdW7mIXE3OdpeDrDeNq4R+HQfBL0lx4eovf87JBYNEfTqyOpz5O38vDy9H3jw9/rfX93o6+FA+ElkCv35rnZjtvdpoBFxRIpjljC9Xrsuqd26MOvV97L1H4kBYgH1HSXGxp3zTwbky4Tutf9+xFem4LpmvZiHgMgnQROggM14aKNLnxcQgeosk1sJG1VWsOXLxIEAfcwgL92hLerYajoViQYW6tknp984jjN8AfIdszlQKR3676K8Emq6NbtNncT3w2enk7OqXQ5owkgoQ5rCZH0axKfRUGVuPfoY13ZxZKJYooOX5V5/Rvr69y9VuT7zegokXkj38ItWU5QIDAQAB";
//
//        mHelper = new IabHelper(PayModeActivity.this, base64EncodedPublicKey);
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
//    }
//
//    @Override
//    public void onDestroy() {
//        dismissProgressDialog();
//        super.onDestroy();
//        if (mHelper != null) {
//            mHelper.dispose();
//            mHelper = null;
//        }
//    }
//
//    @Override
//    public void onPause() {
//        dismissProgressDialog();
//        super.onPause();
//    }
//
//
//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//        // Check which radio button was clicked
//        switch (view.getId()) {
//
//            case R.id.wallet:
//                if(checked){
//                    paymode = "Wallet";
//                    radioButtonRazorPay.setChecked(false);
//                    radioButtonGooglePay.setChecked(false);
//                    radioButtonPaytm.setChecked(false);
//                }
//                break;
//            case R.id.paytm:
//                if (checked) {
//                    paymode = "Paytm";
//                    radioButtonRazorPay.setChecked(false);
//                    radioButtonGooglePay.setChecked(false);
//                    radioButtonWallet.setChecked(false);
//                }
//                    break;
//            case R.id.googlepay:
//                if (checked) {
//                    paymode = "GooglePlay";
//                    radioButtonPaytm.setChecked(false);
//                    radioButtonRazorPay.setChecked(false);
//                    radioButtonWallet.setChecked(false);
//                }
//                    break;
//
//            case R.id.razorpay:
//                if (checked) {
//                    paymode = "RazorPay";
//                    radioButtonPaytm.setChecked(false);
//                    radioButtonGooglePay.setChecked(false);
//                    radioButtonWallet.setChecked(false);
//                }
//                break;
//
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        this.finish();
//
//
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
//
//
//
//
////    private void bidPurchase(String buybidId,String vendor,String orderId,String productId,String transactionId,String packageName,String acknoledge,String developerPayload,String purchaseTime,String purchaseState) {
////
////        APIService apiService = ApiClient.getClient().create(APIService.class);
////        Call<BidPurchaseModel> call = apiService.bidPurchase(Constants.getSharedPreferenceInt(PayModeActivity.this,"userId",0),
////                Constants.getSharedPreferenceString(PayModeActivity.this,"securitytoken",""),
////                Constants.getSharedPreferenceString(PayModeActivity.this,"versionName",""),
////                Constants.getSharedPreferenceInt(PayModeActivity.this,"versionCode",0),buybidId,
////                Constants.getSharedPreferenceString(PayModeActivity.this,"userFrom",""),vendor,orderId,productId,
////                transactionId,packageName,acknoledge,developerPayload,purchaseTime,purchaseState);
////
////        if(!((Activity) PayModeActivity.this).isFinishing()) {
////            progressDialog = new ProgressDialog(PayModeActivity.this);
////            progressDialog.setMessage(getString(R.string.loadingwait));
////            progressDialog.show();
////            progressDialog.setCancelable(false);
////        }
////
////        call.enqueue(new Callback<BidPurchaseModel>() {
////            @Override
////            public void onResponse(Call<BidPurchaseModel>call, Response<BidPurchaseModel> response) {
////                dismissProgressDialog();
//////				Log.d("Bid", "Number of movies received:size "+response +" bid"+response.body());
////
////                if(response!=null){
////                    if(response.isSuccessful()){
////                        if(response.body().getStatus()==200){
////                            Toast.makeText(PayModeActivity.this, getString(R.string.systemmessage)+response.body().getMessage(), Toast.LENGTH_SHORT).show();
////
////
////                        }else{
////                            Toast.makeText(PayModeActivity.this,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
////                        }
////
////                    }
////                }
////                else{
////                    Toast.makeText(PayModeActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
////                }
////
////
////            }
////
////            @Override
////            public void onFailure(Call<BidPurchaseModel>call, Throwable t) {
////                // Log error here since request failed
////                Log.e("response", t.toString());
////            }
////        });
////
////
////    }
//
//
//    private void dismissProgressDialog() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
//    }
//
//}
