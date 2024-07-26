package com.bidwinko.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bidwinko.R;
//import com.bidwinko.activity.PayModeActivity;
import com.bidwinko.model.BuyBid;
import com.bidwinko.model.BuyBidValue;
//import com.bidwinko.testing.SomeActivity;
import com.bidwinko.screens.activity.BalanceActivity;
import com.bidwinko.utilies.Constants;

import java.util.ArrayList;

import retrofit2.Response;

public class BuyBidListAdapter extends RecyclerView.Adapter<BuyBidListAdapter.MyViewHolder> {

    ArrayList<BuyBidValue> buyBidValueArrayList;
    Context context;
    Response<BuyBid> response;


    public BuyBidListAdapter(Response<BuyBid>response,ArrayList<BuyBidValue> buyBidValueArrayList, FragmentActivity activity) {
        this.buyBidValueArrayList = buyBidValueArrayList;
        this.context = activity;
        this.response = response;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView proBids, bidValidity,bidAmount;
        CardView card_main;

        public MyViewHolder(View view) {
            super(view);
            proBids = (TextView) view.findViewById(R.id.bid);
            bidValidity = (TextView) view.findViewById(R.id.bidexpire);
            bidAmount = (TextView) view.findViewById(R.id.bidamount);
            card_main =  view.findViewById(R.id.card_main);
        }
    }

 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buybid_list_item, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BuyBidValue buyBidValue = buyBidValueArrayList.get(position);
        holder.proBids.setText(buyBidValue.getBuybidName());
        holder.bidValidity.setText(buyBidValue.getValidity());
        holder.bidAmount.setText(Constants.getSharedPreferenceString(context, "curency", "")+buyBidValue.getBuybidPrice());



        holder.card_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProductDetails = new Intent(context, BalanceActivity.class);
                intentProductDetails.putExtra("bidamount", buyBidValue.getBuybidPrice());
                intentProductDetails.putExtra("buybidId", buyBidValue.getBuybidId());
                intentProductDetails.putExtra("totalbid", buyBidValue.getBuybidName());
                intentProductDetails.putExtra("walletOpt",response.body().getWalletOpt());
                intentProductDetails.putExtra("gplayOpt", response.body().getGplayOpt());
                intentProductDetails.putExtra("cashFree", response.body().isCashFree());
                intentProductDetails.putExtra("notifyUrl", response.body().getNotifyUrl());
                intentProductDetails.putExtra("ITEM_SKU", buyBidValue.getInAppId());
                ((Activity) context).startActivityForResult(intentProductDetails, 103);

            }
        });


    }


    @Override
    public int getItemCount() {
        if(buyBidValueArrayList !=null) {
            return buyBidValueArrayList.size();
        }
        return 0;
    }
}