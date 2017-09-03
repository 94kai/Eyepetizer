package com.xk.eyepetizer.ui.view.category

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.model.bean.Category
import kotlinx.android.synthetic.main.layout_category_item.view.*

/**
 * Created by xuekai on 2017/9/3.
 */
class CategoryItem : FrameLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.layout_category_item, this)
    }

    fun setData(category: Category) {
        tv_name.text = "#"+category.name
        Glide.with(context).load(category.bgPicture).centerCrop().into(iv_category)
    }
}