package com.xk.eyepetizer.mvp.model.bean

/**
 * Created by xuekai on 2017/9/4.
 */
data class HotCategory(val tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)
    data class Tab(val id: Long, val name: String, val apiUrl: String)
}