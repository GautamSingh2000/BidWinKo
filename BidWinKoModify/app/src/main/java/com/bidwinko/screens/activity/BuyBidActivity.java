package com.bidwinko.screens.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bidwinko.R;
import com.bidwinko.adapter.BuyBidListAdapter;
import com.bidwinko.API.APIService;
import com.bidwinko.model.BuyBid;
import com.bidwinko.model.BuyBidValue;
import com.bidwinko.utilies.Constants;
import com.bidwinko.API.Retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyBidActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<BuyBidValue> buybidArraylists = new ArrayList<>();
    private BuyBidListAdapter mAdapter;
    TextView txtBidBalance;
    CardView unlimitedBid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buybid_fragment);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        ((AppCompatActivity)this).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.buybid) + "</font>")));

        recyclerView = findViewById(R.id.recycler_view);
        txtBidBalance = findViewById(R.id.bidbalance);
        unlimitedBid = findViewById(R.id.card_unlimited_bid);

//        getBuyBids();

        unlimitedBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//				Toast.makeText(getActivity(),"Feature Coming Soon..",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BuyBidActivity.this,ShareEarnActivity.class);
                startActivity(intent);
            }
        });


    }

//    private void getBuyBids() {
//
//        APIService apiService = Retrofit.getClient().create(APIService.class);
//        Call<BuyBid> call = apiService.getbuyBids(Constants.getSharedPreferenceInt(BuyBidActivity.this,"userId",0),
//                Constants.getSharedPreferenceString(BuyBidActivity.this,"securitytoken",""),
//                Constants.getSharedPreferenceString(BuyBidActivity.this,"versionName",""),
//                Constants.getSharedPreferenceInt(BuyBidActivity.this,"versionCode",0),
//                Constants.getSharedPreferenceString(BuyBidActivity.this,"userFrom",""));
//
//        if(!((Activity) BuyBidActivity.this).isFinishing()) {
//            progressDialog = new ProgressDialog(BuyBidActivity.this);
//            progressDialog.setMessage(getString(R.string.loadingwait));
//            progressDialog.show();
//            progressDialog.setCancelable(false);
//        }
//
//        call.enqueue(new Callback<BuyBid>() {
//            @Override
//            public void onResponse(Call<BuyBid>call, Response<BuyBid> response) {
//                dismissProgressDialog();
////                Log.d("Bid", "Number of movies received:size "+response +" bid");
//
//                if(response!=null){
//                    if(response.isSuccessful()){
//                        if(response.body().getStatus()==200){
////                            txtBidBalance.setText(""+response.body().getBidBal());
////                            boolean res = response.body().getFreeBid();
//                            String bidremain = response.body().getBidBalance();
//                            Constants.setSharedPreferenceString(BuyBidActivity.this,"bidBalance",bidremain);
//                            txtBidBalance.setText(""+Constants.getSharedPreferenceString(BuyBidActivity.this,"bidBalance",""));
//                            buybidArraylists = response.body().getBuyBidValues();
////                            Log.d("Bid", "Number of movies received:size " + buybidArraylists.size());
//
////                            boolean walletOption = response.body().getWalletOpt();
//                            mAdapter = new BuyBidListAdapter(response,buybidArraylists, BuyBidActivity.this);
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BuyBidActivity.this);
//                            recyclerView.setLayoutManager(mLayoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());
//                            recyclerView.setAdapter(mAdapter);
//                        }else{
//                            Toast.makeText(BuyBidActivity.this,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }
//                else{
//                    Toast.makeText(BuyBidActivity.this,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<BuyBid>call, Throwable t) {
//                // Log error here since request failed
//                Log.e("response", t.toString());
//            }
//        });
//
//
//    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        dismissProgressDialog();
        super.onPause();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==103){
            finish();
            startActivity(getIntent());
        }
    }

}

