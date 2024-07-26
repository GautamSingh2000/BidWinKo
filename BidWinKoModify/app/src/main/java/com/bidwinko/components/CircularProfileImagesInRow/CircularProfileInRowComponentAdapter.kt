package com.bidwinko.components.CircularProfileImagesInRow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.adapter.BuyBidListAdapter
import com.bumptech.glide.Glide

class CircularProfileInRowComponentAdapter(
    private var context: Context,
    private var profilelist: ArrayList<String>,
) :
    RecyclerView.Adapter<CircularProfileInRowComponentAdapter.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById(R.id.profileimage) as com.google.android.material.imageview.ShapeableImageView
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_circular_profile_image_row_component_layout, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return profilelist.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data =  profilelist.get(position)
        Glide.with(context).load(data).into(holder.image)
    }

}