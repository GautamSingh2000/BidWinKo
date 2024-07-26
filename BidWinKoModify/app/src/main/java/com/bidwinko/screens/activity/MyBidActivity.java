package com.bidwinko.screens.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bidwinko.R;
import com.bidwinko.adapter.UserBidListAdapter;
import com.bidwinko.API.APIService;
import com.bidwinko.model.ShowUserBidsModel;
import com.bidwinko.model.UserBid;
import com.bidwinko.utilies.Constants;
import com.bidwinko.API.Retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBidActivity extends AppCompatActivity {


    ArrayList<UserBid> userBidArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserBidListAdapter mAdapter;
    Context context;
    ProgressDialog progressDialog;
    String bidofferId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bid);

        context = this;
        recyclerView = findViewById(R.id.recycler_view);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        ((AppCompatActivity)this).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.bidhistory) + "</font>")));

        Intent intent = getIntent();
        if(intent!=null){
            bidofferId = intent.getStringExtra("bidofferId");
        }
//      getUserBids(bidofferId);
    }

//    private void getUserBids(String bidofferId) {
//
//        APIService apiService = Retrofit.getClient().create(APIService.class);
//        Call<ShowUserBidsModel> call = apiService.showUserBids(Constants.getSharedPreferenceInt(MyBidActivity.this,"userId",0),
//                Constants.getSharedPreferenceString(MyBidActivity.this,"securitytoken",""),
//                Constants.getSharedPreferenceString(MyBidActivity.this,"versionName",""),
//                Constants.getSharedPreferenceInt(MyBidActivity.this,"versionCode",0),
//                bidofferId,Constants.getSharedPreferenceString(MyBidActivity.this,"userFrom",""));
//
//        if(!((Activity) MyBidActivity.this).isFinishing()) {
//            progressDialog = new ProgressDialog(MyBidActivity.this);
//            progressDialog.setMessage(getString(R.string.loadingwait));
//            progressDialog.show();
//            progressDialog.setCancelable(false);
//        }
//
//        call.enqueue(new Callback<ShowUserBidsModel>() {
//            @Override
//            public void onResponse(Call<ShowUserBidsModel> call, Response<ShowUserBidsModel> response) {
//
//                dismissProgressDialog();
//                Log.d("Bid", "Number of movies received:size "+response +" bid"+response.body());
//
//                if(response!=null){
//
//                    if(response.isSuccessful()){
//
//                        if(response.body().getStatus()==200){
//
//                            userBidArrayList = response.body().getUserBids();
//                            Log.d("Bid", "Number of movies received:size " + userBidArrayList.size());
//
//                            mAdapter = new UserBidListAdapter(userBidArrayList,context);
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                            recyclerView.setLayoutManager(mLayoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());
//                            recyclerView.setAdapter(mAdapter);
//                        }else{
//                            Toast.makeText(context,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }
//                else{
//                    Toast.makeText(context,"Response: "+response.errorBody(),Toast.LENGTH_SHORT).show();
//                }
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ShowUserBidsModel> call, Throwable t) {
//
//            }
//        });
//    }


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
}
