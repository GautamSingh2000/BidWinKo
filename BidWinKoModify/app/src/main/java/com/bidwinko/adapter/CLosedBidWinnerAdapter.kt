package com.bidwinko.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.model.ResponseModels.ClosedWinnerList
import com.bumptech.glide.Glide

class CLosedBidWinnerAdapter(private val winnerList: ArrayList<ClosedWinnerList>, private var context: Context) :
    RecyclerView.Adapter<CLosedBidWinnerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var position: TextView
        var userName: TextView
        var prize: TextView
        var userImage: ImageView

        init {
            userName = view.findViewById<View>(R.id.winner_name) as TextView
            position = view.findViewById<View>(R.id.position) as TextView
            prize = view.findViewById(R.id.prize)
            userImage = view.findViewById(R.id.userimage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_closed_bid_winner_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val winnerDetail = winnerList.get(position)

        if (winnerDetail.image != "") {
            winnerDetail.image?.let {
                holder.userImage.setPadding(0, 0, 0, 0)
                Glide.with(context).load(it).placeholder(R.drawable.holo_person).circleCrop()
                    .circleCrop().into(holder.userImage)
            }
        }
        holder.userName.text = winnerDetail.name
        holder.prize.text = winnerDetail.prize
        holder.position.text = winnerDetail.position
    }

    override fun getItemCount(): Int {
        return winnerList.size
    }
}