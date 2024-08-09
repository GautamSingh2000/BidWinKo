package com.bidwinko.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.model.ResponseModels.winnerDetail
import com.bumptech.glide.Glide

class WinnerListAdapter
    (private val winnerList: ArrayList<winnerDetail>, private var context: Context) :
    RecyclerView.Adapter<WinnerListAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var proName: TextView
        var winnerLocation: TextView
        var userName: TextView
        var winingBid: TextView
        var proImage: ImageView
        var userImage: ImageView
        var card_main: CardView

        init {
            userName = view.findViewById<View>(R.id.Username) as TextView
            proName = view.findViewById<View>(R.id.Productname) as TextView
            winnerLocation = view.findViewById<View>(R.id.winnerlocation) as TextView
            winingBid = view.findViewById<View>(R.id.winbidsprice) as TextView
            proImage = view.findViewById(R.id.productimage)
            userImage = view.findViewById(R.id.userimage)
            card_main = view.findViewById(R.id.card_main)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_winner_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val winnerDetail = winnerList.get(position)

        if(winnerDetail.user_Image != "" && !winnerDetail.user_Image.isNullOrEmpty())
        {
            winnerDetail.user_Image?.let {
                holder.userImage.setPadding(0, 0, 0, 0)
                Glide.with(context).load(it).placeholder(R.drawable.holo_person).circleCrop().circleCrop().into(holder.userImage)
            }
        }

        if(!winnerDetail.product_Image.equals(""))
        {
            winnerDetail.product_Image?.let {
                holder.proImage.setPadding(15, 15, 15, 15)
                Glide.with(context).load(it).placeholder(R.drawable.product_icon).circleCrop().into(holder.proImage)
            }
        }

        holder.userName.text = winnerDetail.user_Name
        holder.proName.text = winnerDetail.product_Name
        holder.winingBid.text = winnerDetail.winnning_Bid
        holder.winnerLocation.text = winnerDetail.location

//
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.placeholder);
//        requestOptions.error(R.drawable.placeholder);
//
//
//        Glide.with(context).asBitmap().load(winnerDetailArrayList.get(position).getUserImageUrl()).centerCrop()
//                .into(new BitmapImageViewTarget(holder.userImage) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                holder.userImage.setImageDrawable(circularBitmapDrawable);
//            }
//        });
//
//        Glide.with(context)
//                .setDefaultRequestOptions(requestOptions)
//                .load(winnerDetailArrayList.get(position).getOfferImageUrl()).into(holder.proImage);
    }

    override fun getItemCount(): Int {
        return winnerList.size
    }
}