package com.bidwinko.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import com.airbnb.lottie.LottieAnimationView
import com.bidwinko.R

class CustomSeekBar : AppCompatSeekBar {

    private var mThumbView: View? = null
    private var thumbAnim: LottieAnimationView? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val inflater = LayoutInflater.from(context)
        mThumbView = inflater.inflate(R.layout.custom_seekbar_thumb, null)
        thumbAnim = mThumbView?.findViewById(R.id.thumb_image)

        // Set the initial drawable from the custom thumb view
        setThumb(getDrawableFromView(mThumbView!!))

        // Set a listener to listen for progress changes
        setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // Start the animation whenever the progress changes by user interaction
                    thumbAnim?.playAnimation()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Start the animation when the user starts touching the SeekBar
                thumbAnim?.playAnimation()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Optionally, stop the animation when the user stops touching the SeekBar
                // thumbAnim?.pauseAnimation()
            }
        })
    }

    private fun getDrawableFromView(view: View): Drawable {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return BitmapDrawable(resources, bitmap)
    }

    fun updateThumbImage(resId: Int) {
        thumbAnim?.setImageResource(resId)
        setThumb(getDrawableFromView(mThumbView!!))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setThumb(getDrawableFromView(mThumbView!!))
    }
}
