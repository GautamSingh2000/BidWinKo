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
    private val list: ArrayList<String>,
    private val mListener: LowerBidRangeitemClickListener?,
) : RecyclerView.Adapter<LoweRangeBidAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text = itemView.findViewById<TextView>(R.id.text)
        var selected = false
    }

    var selectedBidList: ArrayList<String> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_bidrange_layout, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list.get(position)
        holder.text.let {
            if (selectedBidList.size >= 0) {
                if (selectedBidList.contains(data)) {
                    holder.selected = true
                    it.isSelected = true
                } else {
                    holder.selected = false
                    it.isSelected = false
                }
            }
            it.text = data

            it.setOnClickListener {
                mListener?.onLowerBidRangeItemClick(list[position])
                if (holder.selected) {
                    it.isSelected = false
                    holder.selected = !holder.selected
                } else {
                    it.isSelected = true
                    holder.selected = !holder.selected
                }
            }
        }
    }

    fun updateList(newlist: List<String>, selectedlist: List<String>) {
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
