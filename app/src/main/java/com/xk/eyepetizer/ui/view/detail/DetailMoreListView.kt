package com.xk.eyepetizer.ui.view.detail

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.xk.eyepetizer.R

/**
 * 详情页点击某个title后出现的recyclerview
 * Created by xuekai on 2017/8/27.
 */
class DetailMoreListView:RelativeLayout{
    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.layout_detail_more_listview,this)


    }
}