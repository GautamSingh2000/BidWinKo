package com.bidwinko.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bidwinko.R;
import com.bidwinko.model.UserBid;

import java.util.ArrayList;

public class UserBidListAdapter extends RecyclerView.Adapter<UserBidListAdapter.MyViewHolder> {

    private ArrayList<UserBid> userBidArrayList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bidDate, bidValue,bidStatus;


        public MyViewHolder(View view) {
            super(view);
            bidDate = (TextView) view.findViewById(R.id.biddate);
            bidValue = (TextView) view.findViewById(R.id.bidvalue);
            bidStatus = (TextView) view.findViewById(R.id.bidstatus);

        }
    }


    public UserBidListAdapter(ArrayList<UserBid> userBidArrayList, Context context) {
        this.userBidArrayList = userBidArrayList;
        this.context = context;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mybid_list_item, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final UserBid userBid = userBidArrayList.get(position);
        holder.bidDate.setText(userBid.getBidTime());
        holder.bidValue.setText(userBid.getBuyValue());
        holder.bidStatus.setText(userBid.getStatus());

    }
 
    @Override
    public int getItemCount() {
        if(userBidArrayList!=null) {
            return userBidArrayList.size();
        }
        return 0;
    }
}