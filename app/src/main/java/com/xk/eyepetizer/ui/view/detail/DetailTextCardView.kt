package com.xk.eyepetizer.ui.view.detail

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.model.bean.Item
import kotlinx.android.synthetic.main.layout_detail_text_card.view.*

/**
 * Created by xuekai on 2017/8/26.
 */
class DetailTextCardView : LinearLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.layout_detail_text_card, this)
        this.tv_text_card.setTextColor(Color.WHITE)
        this.tv_text_card.textSize = 35f
    }

    fun setText(item: Item?) {
        var result = ""
        item?.data?.text
                ?.split("")
                ?.filter { it != "" }
                ?.forEach { result = result + it + "  " }
        this.tv_text_card.text = result
    }
}