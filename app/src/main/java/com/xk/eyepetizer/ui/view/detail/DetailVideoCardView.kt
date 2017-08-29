package com.xk.eyepetizer.ui.view.detail

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.durationFormat
import kotlinx.android.synthetic.main.layout_detail_video_card.view.*

/**
 * Created by xuekai on 2017/8/26.
 */
class DetailVideoCardView : RelativeLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.layout_detail_video_card, this)
        this.tv_title.isBold = true
        this.tv_title.color = Color.WHITE
        this.tv_title.textSize = 40f



        this.tv_tag.color = 0xeeffffff.toInt()
        this.tv_tag.textSize = 35f

    }

    fun setData(item: Item, playAnimation: Boolean) {
        //设置图片
        Glide.with(context).load(item.data?.cover?.detail).centerCrop().into(this.iv_video_card)


        this.tv_title.withAnimation = playAnimation
        this.tv_tag.withAnimation = playAnimation
        this.tv_title.text = item.data?.title
        this.tv_tag.text = "#${item.data?.category}  /  ${durationFormat(item.data?.duration)} ${if (item.data?.library == "DAILY") " /  开眼精选" else ""}"

    }


}