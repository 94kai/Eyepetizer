package com.xk.eyepetizer.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.ui.view.detail.ListEndView
import com.xk.eyepetizer.ui.view.detail.DetailInfoItem
import com.xk.eyepetizer.ui.view.detail.DetailTextCardView
import com.xk.eyepetizer.ui.view.detail.DetailVideoCardView
import java.net.URLDecoder

/**
 * Created by xuekai on 2017/8/25.
 */
class DetailAdapter : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    val TYPE_INFO_CARD = 0
    val TYPE_TEXT_CARD = 1
    val TYPE_VIDEO_CARD = 2
    val TYPE_END_CARD = 3

    val data: ArrayList<Item> by lazy { ArrayList<Item>() }


    //已经播放过jumpshow动画的position集合（播放过一次之后就不要在播放了）
    val hasPlayAnimationList: ArrayList<Int> by lazy { ArrayList<Int>() }


    /**
     * 只有添加影片信息、作者信息这个item会掉这儿，所以需要先清空数据（比如点了相关推荐的其他item，刷新全部数据，包括影片信息，会先调用这个）
     */
    fun addData(item: Item) {
        data.clear()
        notifyDataSetChanged()
        data.add(item)
        notifyItemInserted(0)
    }

    /**
     * 添加相关推荐item
     */
    fun addData(item: ArrayList<Item>) {
        data.addAll(item)
        notifyItemRangeInserted(1, item.size)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val itemView = holder?.itemView
        when (getItemViewType(position)) {
            TYPE_TEXT_CARD -> {
                (itemView as DetailTextCardView).setText(data[position])
                itemView.setOnClickListener { onCategoryTitleClick?.invoke(URLDecoder.decode(data[position].data?.actionUrl?.split("&url=")!![1], "utf-8"),data[position].data?.text) }

            }
            TYPE_VIDEO_CARD -> {
                val hasPlay = hasPlayAnimationList.contains(position)
                if (!hasPlay) {
                    hasPlayAnimationList.add(position)
                }
                (itemView as DetailVideoCardView).setData(data[position], !hasPlay)
                itemView.setOnClickListener { onVideoClick?.invoke(data[position]) }
            }
            TYPE_INFO_CARD -> {
                val hasPlay = hasPlayAnimationList.contains(position)
                if (!hasPlay) {
                    hasPlayAnimationList.add(position)
                }
                (itemView as DetailInfoItem).let {
                    it.setData(data[position], !hasPlay)
                    it.onMovieAuthorClick = onMovieAuthorClick
                }
            }
            TYPE_END_CARD -> {
                (itemView as ListEndView).setShow(data.size > 1)
            }
            else -> {
                throw IllegalArgumentException("日狗，api蒙错了，出现了第三种情况")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var itemView: View
        when (viewType) {
            TYPE_TEXT_CARD -> {
                itemView = DetailTextCardView(parent?.context)
            }
            TYPE_VIDEO_CARD -> {
                itemView = DetailVideoCardView(parent?.context)
            }
            TYPE_INFO_CARD -> {
                itemView = DetailInfoItem(parent?.context)
            }
            TYPE_END_CARD -> {
                itemView = ListEndView(parent?.context)
                itemView.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
            }
            else -> {
                throw IllegalArgumentException("日狗，api蒙错了，出现了第三种情况")
            }
        }
        return ViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_INFO_CARD
        } else if (position == itemCount - 1) {
            return TYPE_END_CARD
        } else {
            if (data[position].type == "textCard") {
                return TYPE_TEXT_CARD
            } else if (data[position].type == "videoSmallCard") {
                return TYPE_VIDEO_CARD
            }
        }
        throw IllegalArgumentException("日狗，api蒙错了，出现了第三种情况")
    }

    override fun getItemCount(): Int = data.size + 1


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)


    var onVideoClick: ((Item) -> Unit)? = null
    /**
     * 第一个参数为url 第二个是title
     */
    var onCategoryTitleClick: ((String?,String?) -> Unit)? = null

    /**
     * movieinfo authorinfo里的按钮被点击
     */
    var onMovieAuthorClick: ((Int) -> Unit)? = null

    fun setOnItemClick(onVideoClick: (Item) -> Unit = {},
                       onCategoryTitleClick: (String?,String?) -> Unit = {_,_->},
                       onMovieAuthorClick: (Int) -> Unit) {
        this.onVideoClick = onVideoClick
        this.onCategoryTitleClick = onCategoryTitleClick
        this.onMovieAuthorClick = onMovieAuthorClick
    }

}

