package com.bidwinko.screens.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.bidwinko.adapter.MYTransactionAdapter;
import com.bidwinko.API.APIService;

import com.bidwinko.model.Transaction;
import com.bidwinko.model.UserTransactionModel;
import com.bidwinko.utilies.Constants;
import com.bidwinko.API.Retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransactionActivity extends AppCompatActivity {

    ArrayList<Transaction> myTransactionModelArrayList = null;
    MYTransactionAdapter myTransactionAdapter;
    RecyclerView recyclerView;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        try{

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            ((AppCompatActivity)this).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">" +getString(R.string.app_name)+" / "+getString(R.string.mytransaction) + "</font>")));


            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

//            getMyTransactions();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

//    private void getMyTransactions() {
//
//        APIService service = Retrofit.getClient().create(APIService.class);
//        Call<UserTransactionModel> call = service.getUserTransactions(Constants.getSharedPreferenceInt(TransactionActivity.this,"userId",0),
//                        Constants.getSharedPreferenceString(TransactionActivity.this,"securitytoken",""),
//                        Constants.getSharedPreferenceString(TransactionActivity.this,"versionName",""),
//                        Constants.getSharedPreferenceInt(TransactionActivity.this,"versionCode",0),
//                        Constants.getSharedPreferenceString(TransactionActivity.this,"userFrom","") );
//
//
//
//        if(!((Activity) TransactionActivity.this).isFinishing()){
//            progressDialog = new ProgressDialog(TransactionActivity.this);
//            progressDialog.setMessage(getString(R.string.loadingwait));
//            progressDialog.show();
//            progressDialog.setCancelable(false);
//        }
//
//        call.enqueue(new Callback<UserTransactionModel>() {
//            @Override
//            public void onResponse(Call<UserTransactionModel> call, Response<UserTransactionModel> response) {
//                progressDialog.dismiss();
//                Log.e("testing", "onResponse: "+response.body().getMessage() );
//                try {
//                    if(response!=null) {
//                        if (response.isSuccessful()) {
//
//                            if (response.body().getStatus()==200) {
//                                myTransactionModelArrayList = response.body().getTransactions();
//
//                                myTransactionAdapter = new MYTransactionAdapter(myTransactionModelArrayList, TransactionActivity.this);
//                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TransactionActivity.this);
//                                recyclerView.setLayoutManager(mLayoutManager);
//                                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                                recyclerView.setAdapter(myTransactionAdapter);
//                            } else
//                                Toast.makeText(TransactionActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        } else
//                            Toast.makeText(TransactionActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                    else Toast.makeText(TransactionActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
//                } catch (NullPointerException e) {
//                    Log.e("testing", "onFailure:exd "+e );
//                    Toast.makeText(TransactionActivity.this, getString(R.string.systemmessage)+e, Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserTransactionModel> call, Throwable t) {
//                progressDialog.dismiss();
//                try {
//                    Toast.makeText(TransactionActivity.this, getString(R.string.systemmessage)+t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }catch (NullPointerException e) {
//
//                    Toast.makeText(TransactionActivity.this, getString(R.string.systemmessage)+e, Toast.LENGTH_SHORT).show();
//                }
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

    @Override
    protected void onPause() {
        dismissProgressDialog();
        super.onPause();


    }

    @Override
    public void onStop() {
        dismissProgressDialog();
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}