package com.xk.eyepetizer.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.model.bean.Item
import kotlinx.android.synthetic.main.item_home_standard.view.*

/**
 * Created by xuekai on 2017/8/23.
 */
class HomeStandardItem : FrameLayout {


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.item_home_standard, this)
    }

    fun setData(item: Item) {
        val data = item.data
        val homepage = data?.cover?.homepage
        var avatar = data?.author?.icon
        var avatarRes = R.mipmap.pgc_default_avatar

        if (avatar == null || "" == avatar) {
            avatar = data?.provider?.icon
        }

        Glide.with(context).load(homepage).centerCrop().into(iv_cover)

        val ivAvatarCircle = object : BitmapImageViewTarget(iv_avatar) {
            override fun setResource(resource: Bitmap?) {
                super.setResource(resource)
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, resource)
                circularBitmapDrawable.setCircular(true);
                iv_avatar.setImageDrawable(circularBitmapDrawable);
            }
        }
        if (avatar == null || "" == avatar) {
            Glide.with(context).load(avatarRes).asBitmap().centerCrop().into(ivAvatarCircle)
        } else {
            Glide.with(context).load(avatar).asBitmap().centerCrop().into(ivAvatarCircle)
        }
        tv_title.setText(item.data?.title)
        var tagText = ""
        data?.tags?.take(4)?.forEach { tagText += (it.name + " / ") }
        val timeFromat = timeFromat(data?.duration)
        tagText += timeFromat
        tv_tag.setText(tagText)
        tv_category.setText(data?.category)
    }

    fun timeFromat(duration: Long?): String {
        val minute = duration!! / 60
        val second = duration!! % 60
        if (minute <= 9) {
            if(second<=9){
                return "0${minute}' 0${second}''"
            }else{
                return "0${minute}' ${second}''"
            }
        } else {
            if(second<=9){
                return "${minute}' 0${second}''"
            }else{
                return "${minute}' ${second}''"
            }
        }



    }
}