package com.bidwinko.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.model.ResponseModels.BidPlan
import com.bidwinko.model.ResponseModels.Plan
import com.bidwinko.screens.activity.BalanceActivity

class BuyBidPlanAdapter(
    val buyBidValueArrayList: ArrayList<BidPlan>,
    var context: Context
) : RecyclerView.Adapter<BuyBidPlanAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val category_title = view.findViewById<TextView>(R.id.bid_pacage_name)
        val Rv = view.findViewById<RecyclerView>(R.id.bidPlansRV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_buy_bid_rv_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        Log.e("adaptyer","${buyBidValueArrayList.size } size in 1 adapter")
        val category = buyBidValueArrayList.get(position)
        holder.category_title.text = category.categoryTitle
        val adapter = BuyBidListAdapter(category.plans as ArrayList<Plan>,context)
        holder.Rv.adapter = adapter
    }

    override fun getItemCount(): Int {
        return buyBidValueArrayList.size
    }
}



