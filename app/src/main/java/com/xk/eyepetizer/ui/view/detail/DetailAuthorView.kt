package com.xk.eyepetizer.ui.view.detail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.xk.eyepetizer.R
import kotlinx.android.synthetic.main.layout_detail_author_info.view.*

/**
 * Created by xuekai on 2017/8/25.
 */
class DetailAuthorView : RelativeLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    var onAuthorClick: ((Int) -> Unit)? = null

    private fun init() {
        View.inflate(context, R.layout.layout_detail_author_info, this)

        this.tv_author_name.isBold = true
        this.tv_author_name.color = Color.WHITE
        this.tv_author_name.textSize = 35f

        this.tv_author_intro.color = Color.WHITE
        this.tv_author_intro.textSize = 30f
        this.tv_author_intro.singline = true

        initListner()
    }

    private fun initListner() {
        attention.setOnClickListener { onAuthorClick?.invoke(BTN_WATCH) }
        setOnClickListener { onAuthorClick?.invoke(BTN_AUTHOR) }
    }

    fun setAuthorName(name: String?, playAnimation: Boolean) {
        this.tv_author_name.withAnimation = playAnimation
        this.tv_author_name.text = name
    }


    fun setIntro(intro: String?, playAnimation: Boolean) {
        this.tv_author_intro.withAnimation = playAnimation
        this.tv_author_intro.text = intro
    }

    fun setAvator(url: String) {
        val ivAvatarCircle = object : BitmapImageViewTarget(this.iv_avatar) {
            override fun setResource(resource: Bitmap?) {
                super.setResource(resource)
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, resource)
                circularBitmapDrawable.setCircular(true);
                iv_avatar.setImageDrawable(circularBitmapDrawable);
            }
        }
        Glide.with(context).load(url).asBitmap().centerCrop().into(ivAvatarCircle)

    }

}