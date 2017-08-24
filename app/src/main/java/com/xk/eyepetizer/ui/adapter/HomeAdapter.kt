package com.xk.eyepetizer.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.xk.eyepetizer.mvp.model.bean.HomeBean
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.ui.view.HomeStandardItem
import com.xk.eyepetizer.ui.view.HomeTextHeaderItem
import com.xk.eyepetizer.ui.view.banner.HomeBanner
import com.xk.eyepetizer.util.DisplayManager

/**
 * Created by xuekai on 2017/8/23.
 */
class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    var isNewBanner = false

    //只会在banner数据请求到的时候set，其他都是add，所以通过set可以获取到banner的count
    var itemList: ArrayList<Item> = ArrayList()
        set(value) {
            field = value
            isNewBanner = true
            notifyDataSetChanged()
        }

    //banner用了的item的数量（包括type为banner2的）
    var bannerItemListCount = 0

    fun addData(homeBean: HomeBean) {
        itemList.addAll(homeBean.issueList[0].itemList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val itemViewType = getItemViewType(position)
        when (itemViewType) {
            TYPE_BANNER -> {
                if (isNewBanner) {
                    isNewBanner = false
                    (holder?.itemView as HomeBanner).setData(itemList.take(bannerItemListCount).toCollection(ArrayList()))
                }
            }
            TYPE_STANDARD -> (holder?.itemView as HomeStandardItem).let {
                it.setData(itemList[position + bannerItemListCount - 1])
            }
//            setText(itemList[position + bannerItemListCount - 1].data?.title)

            TYPE_HEADER_TEXT -> (holder?.itemView as HomeTextHeaderItem).setHeaderText(itemList[position + bannerItemListCount - 1].data?.text)

        }

    }

    override fun getItemCount(): Int {
        if (itemList.size > bannerItemListCount) {
            return itemList.size - bannerItemListCount + 1
        } else if (itemList.size == 0) {
            return 0
        } else {
            return 1
        }
    }


    private val TYPE_BANNER = 1
    private val TYPE_STANDARD = 2
    private val TYPE_HEADER_TEXT = 3

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_BANNER
        }
        if (itemList[position + bannerItemListCount - 1].type == "textHeader") {
            return TYPE_HEADER_TEXT
        } else {
            return TYPE_STANDARD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        when (viewType) {
            TYPE_BANNER -> return ViewHolder(HomeBanner(parent!!.context))

            TYPE_STANDARD -> {
                val textView = HomeStandardItem(parent!!.context)
                return ViewHolder(textView)
            }
            TYPE_HEADER_TEXT -> {
                val headerText = HomeTextHeaderItem(parent!!.context)
                headerText.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                        DisplayManager?.dip2px(56f)!!)
                return ViewHolder(headerText)
            }
            else -> return ViewHolder(null)
        }
    }


    fun setBannerSize(size:Int){
        bannerItemListCount=size
    }
    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}