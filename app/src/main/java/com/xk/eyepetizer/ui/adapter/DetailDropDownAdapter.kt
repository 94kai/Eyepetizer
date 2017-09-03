package com.xk.eyepetizer.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.ui.view.detail.DetailReplyTitleView
import com.xk.eyepetizer.ui.view.detail.DetailReplyView
import com.xk.eyepetizer.ui.view.detail.DetailVideoCardView
import com.xk.eyepetizer.ui.view.detail.ListEndView

/**
 * Created by xuekai on 2017/8/28.
 */
class DetailDropDownAdapter : RecyclerView.Adapter<DetailDropDownAdapter.ViewHolder>() {

    //    val TYPE_REPLY
    val TYPE_VIDEO = 1
    val TYPE_REPLY = 2
    val TYPE_END = 3
    val TYPE_REPLY_TITLE = 4//最新评论、热门评论
    val data by lazy {
        ArrayList<Item>()
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val itemView = holder?.itemView
        when (getItemViewType(position)) {
            TYPE_VIDEO -> {
                (itemView as DetailVideoCardView).setData(data[position], false)
                itemView.setOnClickListener { onVideoClick?.invoke(data[position]) }
            }
            TYPE_REPLY_TITLE -> {
                (itemView as DetailReplyTitleView).setText(data[position])
            }
            TYPE_REPLY -> {
                (itemView as DetailReplyView).setData(data[position])
//                itemView.setOnClickListener { onVideoClick?.invoke(data[position]) }
            }
            else -> {
//                throw IllegalArgumentException("日狗，api蒙错了，出现了第三种情况")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var itemView: View
        when (viewType) {
            TYPE_VIDEO -> {
                itemView = DetailVideoCardView(parent?.context)
            }
            TYPE_REPLY -> {
                itemView = DetailReplyView(parent?.context)
            }
            TYPE_END -> {
                itemView = ListEndView(parent?.context)
                itemView.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
            }
            TYPE_REPLY_TITLE -> {
                itemView = DetailReplyTitleView(parent?.context)
            }
            else -> {
                throw IllegalArgumentException("日狗，api蒙错了，赶紧改")
            }
        }
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        if (data[position].data == null) {
            return TYPE_END
        }
        when (data[position].type) {
            "reply" -> {
                return TYPE_REPLY
            }
            "leftAlignTextHeader" -> {
                return TYPE_REPLY_TITLE
            }
            "videoSmallCard" -> {
                return TYPE_VIDEO
            }
        }
        return super.getItemViewType(position)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    fun addData(items: ArrayList<Item>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun addData(item: Item) {
        if (data[data.size - 1].data == null) {//最后一个数据是空的情况只能有一个，用来展示the end
            return
        }
        data.add(item)
        notifyDataSetChanged()
    }

    fun setData(items: ArrayList<Item>) {
        data.clear()
        notifyDataSetChanged()
        addData(items)
    }

    var onVideoClick: ((Item) -> Unit)? = {}

}