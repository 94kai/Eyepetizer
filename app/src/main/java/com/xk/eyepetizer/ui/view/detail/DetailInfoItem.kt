package com.xk.eyepetizer.ui.view.detail

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.timeFromat
import kotlinx.android.synthetic.main.layout_detail_info.view.*

/**
 * Created by xuekai on 2017/8/25.
 */
class DetailInfoItem : LinearLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.layout_detail_info, this)
    }

    fun setData(item: Item, playAnimation: Boolean) {
        val detailMovieInfoView = detailMovieInfoView as DetailMovieInfoView
        detailMovieInfoView.setIntro(item.data?.description,playAnimation)
        detailMovieInfoView.setTitle(item.data?.title,playAnimation)
        detailMovieInfoView.setTag("#${item.data?.category}  /  ${timeFromat(item.data?.duration)} ${if (item.data?.library == "DAILY") " /  开眼精选" else ""}",playAnimation)
        detailMovieInfoView.setFavorites(item.data?.consumption?.collectionCount)
        detailMovieInfoView.setShare(item.data?.consumption?.shareCount)
        detailMovieInfoView.setReply(item.data?.consumption?.replyCount)


        if (item.data?.author != null) {
            detailAuthorView.setIntro(item.data?.author?.description,playAnimation)
            detailAuthorView.setAuthorName(item.data?.author?.name,playAnimation)
            detailAuthorView.setAvator(item.data?.author?.icon)
        } else {
            detailAuthorView.visibility = View.GONE
        }

    }
}