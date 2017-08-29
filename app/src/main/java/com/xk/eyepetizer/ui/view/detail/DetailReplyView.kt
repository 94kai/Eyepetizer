package com.xk.eyepetizer.ui.view.detail

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.timeFormat
import kotlinx.android.synthetic.main.layout_detail_reply_card.view.*

/**
 * Created by xuekai on 2017/8/26.
 */
class DetailReplyView : RelativeLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.layout_detail_reply_card, this)
    }

    fun setData(item: Item) {
        //设置图片
        val ivAvatarCircle = object : BitmapImageViewTarget(this.iv_avatar) {
            override fun setResource(resource: Bitmap?) {
                super.setResource(resource)
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, resource)
                circularBitmapDrawable.setCircular(true);
                iv_avatar.setImageDrawable(circularBitmapDrawable);
            }
        }
        Glide.with(context).load(item.data?.user?.avatar).asBitmap().centerCrop().into(ivAvatarCircle)



        this.tv_nickname.text = item.data?.user?.nickname
        this.tv_message.text = item.data?.message
        this.tv_time.text = timeFormat(item.data?.createTime!!)
        this.iv_like.text = "" + if(item.data?.likeCount==0) "" else item.data?.likeCount


        if (item.data.parentReply != null) {
            this.parentReply.visibility = View.VISIBLE
            this.tv_reply.visibility=View.VISIBLE
            this.tv_reply.text="回复：${item.data?.parentReply?.user.nickname}"


            //设置图片
            val ivAvatarCircle = object : BitmapImageViewTarget(this.iv_parentreply_avatar) {
                override fun setResource(resource: Bitmap?) {
                    super.setResource(resource)
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, resource)
                    circularBitmapDrawable.setCircular(true)
                    iv_parentreply_avatar.setImageDrawable(circularBitmapDrawable);
                }
            }
            Glide.with(context).load(item.data?.parentReply?.user.avatar).asBitmap().centerCrop().into(ivAvatarCircle)
            this.tv_parentreply_nickname.text = item.data?.parentReply?.user.nickname
            this.tv_parentreply_message.text = item.data?.parentReply.message

        } else {
            this.tv_reply.visibility=View.GONE
            this.parentReply.visibility = View.GONE
        }
    }


}