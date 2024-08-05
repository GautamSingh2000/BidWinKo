package com.bidwinko.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.model.ResponseModels.Plan
import com.bidwinko.screens.activity.BalanceActivity


class BuyBidListAdapter(
    val plans: ArrayList<Plan>,
    var context: Context
) : RecyclerView.Adapter<BuyBidListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var no_of_bids: TextView = view.findViewById(R.id.no_of_bid)
        var expiry: TextView = view.findViewById(R.id.expiry_tv)
        var offer_text: TextView = view.findViewById(R.id.offer_text)
        var bidAmount: TextView = view.findViewById(R.id.amount_tv)
        var card_main: CardView = view.findViewById(R.id.carview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_buy_bid_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val buyBidValue = plans[position]
        holder.no_of_bids.text = "${buyBidValue.noOfBids} Bids"
        holder.expiry.text = "Expire in ${buyBidValue.expiresIn}"
        holder.offer_text.text = "${buyBidValue.offPercentage}% OFF"
        holder.bidAmount.text = "${buyBidValue.planPrice}"

        holder.card_main.setOnClickListener {
            val intentProductDetails = Intent(context, BalanceActivity::class.java)
//            intentProductDetails.putExtra("bidamount", buyBidValue.buybidPrice)
//            intentProductDetails.putExtra("buybidId", buyBidValue.buybidId)
//            intentProductDetails.putExtra("totalbid", buyBidValue.buybidName)
//            intentProductDetails.putExtra("walletOpt", response.body!!.walletOpt)
//            intentProductDetails.putExtra("gplayOpt", response.body!!.gplayOpt)
//            intentProductDetails.putExtra("cashFree", response.body!!.cashFree)
//            intentProductDetails.putExtra("notifyUrl", response.body!!.notifyUrl)
//            intentProductDetails.putExtra("ITEM_SKU", buyBidValue.inAppId)
            (context as Activity).startActivityForResult(intentProductDetails, 103)
        }
    }
    override fun getItemCount(): Int {
        return plans.size
    }
}