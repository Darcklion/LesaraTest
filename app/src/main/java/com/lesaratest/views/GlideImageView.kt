package com.lesaratest.views

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.lesaratest.R

open class GlideImageView : ImageView {

    private val imageUrlBase = "https://daol3a7s7tps6.cloudfront.net/"
    private var imageUrl: String? = null
    private var target: Target<Bitmap>? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        setImageResource(R.mipmap.image_placeholder)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (imageUrl != null && target == null) loadImage(imageUrl)
    }

    private fun loadImage(imageUrl: String?) {
        if (imageUrl != null) {
            init()
            target = Glide.with(context).load(imageUrlBase.plus(imageUrl)).asBitmap().placeholder(R.mipmap.image_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).thumbnail(0.1f)
                    .into(object : SimpleTarget<Bitmap>(measuredWidth, measuredHeight) {
                        override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                            setImageBitmap(resource)
                        }
                    })
        }
    }

    fun setImageUrl(imageUrl: String) {
        if (imageUrl == this.imageUrl) return
        this.imageUrl = imageUrl
        if (target != null) {
            target!!.request.clear()
            target = null
        }
        if (measuredHeight > 0 && measuredWidth > 0) loadImage(imageUrl)
    }
}