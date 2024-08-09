package com.bidwinko.components.NoDataFound

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.bidwinko.R

class NoDataFound (context: Context) : RelativeLayout(context) {

    private var anim: LottieAnimationView
    private var image: ImageView
    private var textView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_no_data_found, this, true)

        anim = findViewById(R.id.anim)
        image = findViewById(R.id.image)
        textView = findViewById(R.id.tv)

        anim.let {
            it.playAnimation()
            it.addAnimatorListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    it.playAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })
        }
    }

    fun show(layout: ViewGroup) {
        layout.addView(this)
        visibility = View.VISIBLE
    }

    fun hide() {
        visibility = View.GONE
    }
}