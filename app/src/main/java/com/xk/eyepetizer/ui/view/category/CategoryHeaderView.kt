package com.xk.eyepetizer.ui.view.category

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.model.bean.Category
import com.xk.eyepetizer.util.DisplayManager
import kotlinx.android.synthetic.main.layout_category_header.view.*

/**
 * Created by xuekai on 2017/9/3.
 */
class CategoryHeaderView : RelativeLayout {
    var tvNameStopTop = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    fun initView() {
        View.inflate(context, R.layout.layout_category_header, this)

        back.setOnClickListener { (context as Activity).finish() }
    }

    /**
     * 设置折叠的进度，0表示没有折叠，1表示完全折叠
     * tv_name marginTop从100dp->tvNameStopTop
     */
    fun setCollaspsedProgress(progress: Float) {
        if ((progress < 0f)) {
            return
        }
        Log.i("CategoryHeaderView", "setCollaspsedProgress-->${progress}")

        getTvNameStopMarginTop()


        val dp100 = DisplayManager.dip2px(100f)!!
        val tvNameCurrentMargin = (tvNameStopTop - dp100) * progress + dp100
        val layoutParams = tv_name.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin = tvNameCurrentMargin.toInt()
        tv_name.layoutParams = layoutParams

        tv_description.alpha = 1 - progress
        attention.alpha = 1 - progress

        //背景图片总共需要偏移的量
        val bgAllOffset = (getMaxHeight() - getMinHeight()) / 2
        iv_header_image.translationY = -bgAllOffset * progress
        if (progress >= 1f) {
            toolbar_bg.setBackgroundColor(0xffffffff.toInt())
            tv_name.setTextColor(Color.BLACK)
            tv_name.setTypeface(null, Typeface.NORMAL);
            back.setImageResource(R.mipmap.ic_action_back)
        } else {
            toolbar_bg.setBackgroundColor(0x00ffffff)
            tv_name.setTextColor(Color.WHITE)
            tv_name.setTypeface(null, Typeface.BOLD)
            back.setImageResource(R.mipmap.ic_action_back_white)
        }
    }

    /**
     * 获取tv_name完全收回去之后的margintop
     */
    private fun getTvNameStopMarginTop() {
        if (tvNameStopTop == 0) {
            tvNameStopTop = tv_temp.top
        }
    }

    fun setData(category: Category) {
        Glide.with(context).load(category.headerImage).into(iv_header_image)
        tv_name.text = category.name
        tv_description.text = category.description
    }


    //    var minHeight=0
//    var maxHeight=0
    fun getMinHeight(): Int {
        return toolbar_bg.height
    }

    fun getMaxHeight(): Int {
        return height
    }
}