package com.bidwinko.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R

class LoweRangeBidAdapter(
    private val context: Context,
    private var clickable: Boolean,
    private val list: ArrayList<String>,
    private var pre_selected_bids: ArrayList<String>,
    private var canceled_bids: ArrayList<String>,
    private val mListener: LowerBidRangeitemClickListener?,
) : RecyclerView.Adapter<LoweRangeBidAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text = itemView.findViewById<TextView>(R.id.text)
        var selected = false
    }

    var selectedBidList: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_lower_bidrange_layout, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list.get(position)
        holder.text.let {
            it.text = data


            Log.e("adapter1", "${canceled_bids.size} size of canceled bids")
            if (canceled_bids.size > 0) {
                if (canceled_bids.contains(data)) {
                    Log.e("adapter", "canceled bids list contain this $data")
                    it.background = context.getDrawable(R.drawable.custom_light_red_box)
                } else {
                    if (pre_selected_bids.size > 0) {
                        if (pre_selected_bids.contains(data)) {
                            it.background = context.getDrawable(R.drawable.green_rounded_box)
                            Log.e("adapter", "pre_selected_bids list contain this $data")
                            holder.selected = true
                            it.isSelected = true
                        } else {
                            if (selectedBidList.size >= 0) {
                                if (selectedBidList.contains(data)) {
                                    holder.selected = true
                                    it.isSelected = true
                                    it.background =
                                        context.getDrawable(R.drawable.green_rounded_box)
                                } else {
                                    holder.selected = false
                                    it.isSelected = false
                                    it.background =
                                        context.getDrawable(R.drawable.gray_rounded_border)
                                }
                            }
                        }
                    }
                }
            }else{
                if (pre_selected_bids.size > 0) {
                    if (pre_selected_bids.contains(data)) {
                        it.background = context.getDrawable(R.drawable.green_rounded_box)
                        Log.e("adapter", "pre_selected_bids list contain this $data")
                        holder.selected = true
                        it.isSelected = true
                    } else {
                        if (selectedBidList.size >= 0) {
                            if (selectedBidList.contains(data)) {
                                holder.selected = true
                                it.isSelected = true
                                it.background =
                                    context.getDrawable(R.drawable.green_rounded_box)
                            } else {
                                holder.selected = false
                                it.isSelected = false
                                it.background =
                                    context.getDrawable(R.drawable.gray_rounded_border)
                            }
                        }
                    }
                }else{
                    if (selectedBidList.size >= 0) {
                        if (selectedBidList.contains(data)) {
                            holder.selected = true
                            it.isSelected = true
                            it.background =
                                context.getDrawable(R.drawable.green_rounded_box)
                        } else {
                            holder.selected = false
                            it.isSelected = false
                            it.background =
                                context.getDrawable(R.drawable.gray_rounded_border)
                        }
                    }
                }
            }


            it.setOnClickListener {
                Log.e("adapter", "$clickable available bid in adapter")
                mListener?.onLowerBidRangeItemClick(list[position])
                if (clickable) {
                    if (holder.selected) {
                        Log.e("adapter", "D selected")
                        it.background = context.getDrawable(R.drawable.gray_rounded_border)
                        holder.selected = !holder.selected
                    } else {
                        Log.e("adapter", " selected")
                        it.background = context.getDrawable(R.drawable.green_rounded_box)
                        holder.selected = !holder.selected
                    }
                } else {
                    Log.e("adapter", "stop please stop for god pleaseeeeeee")
                }
            }
        }
    }

    fun clickableBid(bid: Boolean) {
        this.clickable = bid
    }

    fun updateList(
        newlist: List<String>,
        selectedlist: List<String>,
    ) {
        list.clear()
        list.addAll(newlist)
        selectedBidList.clear()
        selectedBidList.addAll(selectedlist)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface LowerBidRangeitemClickListener {
        fun onLowerBidRangeItemClick(item: String)
    }
}
