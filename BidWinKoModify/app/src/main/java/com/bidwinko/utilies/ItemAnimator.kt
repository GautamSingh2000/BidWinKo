package com.bidwinko.utilies

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R


class ItemAnimator : RecyclerView.ItemAnimator() {
    override fun animateDisappearance(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo?,
    ): Boolean {
        return false
    }

    override fun animateAppearance(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo?,
        postLayoutInfo: ItemHolderInfo,
    ): Boolean {
        viewHolder.itemView.setAlpha(0.0f)
        viewHolder.itemView.scaleX = 0.5f
        viewHolder.itemView.scaleY = 0.5f
        val animation: Animation =
            AnimationUtils.loadAnimation(viewHolder.itemView.context,R.anim.fadein_pop_out)
        viewHolder.itemView.startAnimation(animation)
        return true
    }

    override fun animatePersistence(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo,
    ): Boolean {
        return false
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo,
    ): Boolean {
        return false
    }

    override fun runPendingAnimations() {
        // No-op
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        // No-op
    }

    override fun endAnimations() {
        // No-op
    }

    override fun isRunning(): Boolean {
       return true
    }
}