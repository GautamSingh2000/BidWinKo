package com.bidwinko.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.model.ResponseModels.myBidsResponse

class UserBidListAdapter(private val userBidArrayList: ArrayList<myBidsResponse.Bid>?, var context: Context) :
    RecyclerView.Adapter<UserBidListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item: TextView

        init {
            item = view.findViewById<TextView>(R.id.text) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_upper_bidrange_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userBid = userBidArrayList!![position]
        holder.item.text = userBid.bidNo
        if(userBid.status.equals("Unique"))
        {
            holder.item.background = context.getDrawable(R.drawable.green_rounded_box)
        }else{
            holder.item.background = context.getDrawable(R.drawable.custom_light_red_box)
        }
    }

    override fun getItemCount(): Int {
        return userBidArrayList?.size ?: 0
    }
}