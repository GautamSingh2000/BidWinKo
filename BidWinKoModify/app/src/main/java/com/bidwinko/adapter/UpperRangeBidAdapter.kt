package com.bidwinko.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R

class UpperRangeBidAdapter(
    private val context: Context,
    private val list: ArrayList<String>,
    private val mListener: UpperBidRangeitemClickListener?
) : RecyclerView.Adapter<UpperRangeBidAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text = itemView.findViewById<TextView>(R.id.text)
        var selected = false
    }
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_upper_bidrange_layout, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list.get(position)

        holder.text.let {
            it.text = data
            it.isSelected = position == selectedPosition

            it.setOnClickListener {
                    mListener?.onUpperBidRangeItemClick(list[position],position)

                if (position != selectedPosition) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = position
                    notifyItemChanged(selectedPosition)
                }
                if (holder.selected) {
                    it.isSelected = false
                    holder.selected = !holder.selected
                }
                else {
                    it.isSelected = true
                    holder.selected = !holder.selected
                }
            }
        }
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
    interface UpperBidRangeitemClickListener {
        fun onUpperBidRangeItemClick(item: String, position: Int)
    }
}

