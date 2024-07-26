package com.bidwinko.screens.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bidwinko.R;
import com.bidwinko.screens.activity.ShareEarnActivity;
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

public class BuyBidFragment extends Fragment  {

	View view = null;
	RecyclerView recyclerView;
	ProgressDialog progressDialog;
	ArrayList<BuyBidValue> buybidArraylists = new ArrayList<>();
	private BuyBidListAdapter mAdapter;
	TextView txtBidBalance;
	CardView unlimitedBid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 view = getActivity().getLayoutInflater().inflate(R.layout.buybid_fragment,null);

		((AppCompatActivity)getActivity()).getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">"+getString(R.string.app_name)+" / " + getString(R.string.buybid) + "</font>")));
		 recyclerView = view.findViewById(R.id.recycler_view);
		 txtBidBalance = view.findViewById(R.id.bidbalance);
		 unlimitedBid = view.findViewById(R.id.card_unlimited_bid);

//		 getBuyBids();

		unlimitedBid.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				Toast.makeText(getActivity(),"Feature Coming Soon..",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(),ShareEarnActivity.class);
				startActivity(intent);
			}
		});

		return view;
	}

	public static BuyBidFragment newInstance() {
		return new BuyBidFragment();
	}

//	private void getBuyBids() {
//
//		APIService apiService = Retrofit.getClient().create(APIService.class);
//		Call<BuyBid> call = apiService.getbuyBids(Constants.getSharedPreferenceInt(getActivity(),"userId",0),
//				Constants.getSharedPreferenceString(getActivity(),"securitytoken",""),
//				Constants.getSharedPreferenceString(getActivity(),"versionName",""),
//				Constants.getSharedPreferenceInt(getActivity(),"versionCode",0),
//				Constants.getSharedPreferenceString(getActivity(),"userFrom",""));
//
//		if(!((Activity) getActivity()).isFinishing()) {
//			progressDialog = new ProgressDialog(getActivity());
//			progressDialog.setMessage(getString(R.string.loadingwait));
//			progressDialog.show();
//			progressDialog.setCancelable(false);
//		}
//
//		call.enqueue(new Callback<BuyBid>() {
//			@Override
//			public void onResponse(Call<BuyBid>call, Response<BuyBid> response) {
//				dismissProgressDialog();
//
//				if(response!=null){
//					if(response.isSuccessful()){
//						if(response.body().getStatus()==200){
////							txtBidBalance.setText(""+response.body().getBidBal());
//							buybidArraylists = response.body().getBuyBidValues();
//							String bidremain = response.body().getBidBalance();
//
////							Log.e("bidremain", "onResponse:pd_BB "+bidremain );
//							Constants.setSharedPreferenceString(getActivity(),"bidBalance",bidremain);
//							txtBidBalance.setText(""+Constants.getSharedPreferenceString(getActivity(),"bidBalance",""));
//							mAdapter = new BuyBidListAdapter(response,buybidArraylists,getActivity());
//							RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//							recyclerView.setLayoutManager(mLayoutManager);
//							recyclerView.setItemAnimator(new DefaultItemAnimator());
//							recyclerView.setAdapter(mAdapter);
//						}else{
//							Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
//						}
//
//					}
//				}
//				else{
//					Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
//				}
//
//
//			}
//
//			@Override
//			public void onFailure(Call<BuyBid>call, Throwable t) {
//				// Log error here since request failed
//				Log.e("response", t.toString());
//			}
//		});
//
//
//	}

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
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==103){
			getActivity().finish();
			startActivity(getActivity().getIntent());
		}
	}
}