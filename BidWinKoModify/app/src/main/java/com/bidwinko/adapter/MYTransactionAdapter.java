package com.bidwinko.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bidwinko.R;
import com.bidwinko.model.Transaction;


import java.util.ArrayList;


public class MYTransactionAdapter extends RecyclerView.Adapter<MYTransactionAdapter.ViewHolder> {
    ArrayList<Transaction> items;
    Context context;

    public MYTransactionAdapter(ArrayList<Transaction> items, Context context) {
        this.context = context;
        this.items = items;
       }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_transaction_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.srno.setText(""+items.get(position).getId());
        holder.transName.setText(items.get(position).getTransName());
        holder.transamount.setText(""+items.get(position).getAmount());
        holder.transactiondate.setText(items.get(position).getTransDate());
        holder.transactiontime.setText(items.get(position).getTransTime());

    }

    @Override
    public int getItemCount() {
        if(items!=null){
            return items.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView srno,transName,transamount,transactiondate,transactiontime;


        public ViewHolder(View itemView) {
            super(itemView);
            srno = itemView.findViewById(R.id.srno);
            transName = itemView.findViewById(R.id.transName);
            transamount = itemView.findViewById(R.id.transamount);
            transactiondate = itemView.findViewById(R.id.transactiondate);
            transactiontime = itemView.findViewById(R.id.transactiontime);

        }

    }


}
