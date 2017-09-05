package com.xk.eyepetizer.ui.view.common

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.xk.eyepetizer.R
import com.xk.eyepetizer.durationFormat
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.timePreFormat
import kotlinx.android.synthetic.main.item_home_standard.view.*

/**
 * Created by xuekai on 2017/8/23.
 */
class StandardVideoItem : FrameLayout {


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.item_home_standard, this)
    }

    /**
     * type表示该item用于哪里，稍有不同
     */
    fun setData(item: Item, type: String) {
        val data = item.data

        if (data?.cover?.homepage == null) {

        }
        val cover = data?.cover?.feed
        var avatar = data?.author?.icon
        var avatarRes = R.mipmap.pgc_default_avatar

        if (avatar == null || "" == avatar) {
            avatar = data?.provider?.icon
        }

        Glide.with(context).load(cover).centerCrop().into(iv_cover)

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
        if (type.equals("feed")) {
            tagText = ""
        } else if (type.equals("categorydetail")) {
            if(data?.author!=null){
                tagText = data?.author?.name + " / "
            }else if(data?.provider!=null){
                tagText = data?.provider?.name + " / "
            }else{
                tagText = ""
            }
        }
        data?.tags?.take(4)?.forEach { tagText += (it.name + " / ") }
        val timeFromat = durationFormat(data?.duration)
        tagText += timeFromat
        tv_tag.setText(tagText)

        if (type.equals("feed")) {
            tv_tag2.setText(data?.category)
        } else if (type.equals("categorydetail")) {
            data?.date?.let {
                tv_tag2.setText(timePreFormat(it))
            }
        }

    }
}