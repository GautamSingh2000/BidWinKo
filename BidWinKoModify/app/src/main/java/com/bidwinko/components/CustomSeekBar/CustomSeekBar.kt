package com.bidwinko.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import com.bidwinko.R

class CustomSeekBar : AppCompatSeekBar {

    private var mThumbView: View? = null
    private var mImageView: ImageView? = null

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
        mImageView = mThumbView?.findViewById(R.id.thumb_image)
        setThumb(getDrawableFromView(mThumbView!!))
    }

    private fun getDrawableFromView(view: View): Drawable {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return BitmapDrawable(resources, bitmap)
    }

//    fun updateThumbText(text: String) {
//        mTextView?.text = text
//        setThumb(getDrawableFromView(mThumbView!!))
//    }

    fun updateThumbImage(resId: Int) {
        mImageView?.setImageResource(resId)
        setThumb(getDrawableFromView(mThumbView!!))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setThumb(getDrawableFromView(mThumbView!!))
    }
}
