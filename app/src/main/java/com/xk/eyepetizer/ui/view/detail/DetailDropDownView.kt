package com.xk.eyepetizer.ui.view.detail

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.ui.adapter.DetailDropDownAdapter
import kotlinx.android.synthetic.main.layout_detail_drop_down.view.*

/**
 * 详情页点击某个title后出现的recyclerview/评论的recyclerview]
 * Created by xuekai on 2017/8/27.
 */
class DetailDropDownView : RelativeLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    val adapter by lazy {
        DetailDropDownAdapter()
    }

    private fun init() {
        View.inflate(context, R.layout.layout_detail_drop_down, this)
        rv_detail_more.layoutManager = LinearLayoutManager(context)
        rv_detail_more.adapter = adapter

    }

    fun setDropDownData(items: ArrayList<Item>) {
        adapter.setData(items)
    }

    fun addDropDownData(items: ArrayList<Item>) {
        adapter.addData(items)
    }
    fun addDropDownData(item: Item) {
        adapter.addData(item)
    }

    var onVideoClick: ((Item) -> Unit)? = {}
        set(value) {
            (rv_detail_more.adapter as DetailDropDownAdapter).onVideoClick = value
        }

}