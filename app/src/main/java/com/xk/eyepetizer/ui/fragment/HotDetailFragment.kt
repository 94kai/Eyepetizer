package com.xk.eyepetizer.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xk.eyepetizer.TAG
import com.xk.eyepetizer.mvp.contract.HotContract
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.mvp.presenter.HotCategoryPresenter
import com.xk.eyepetizer.showToast
import com.xk.eyepetizer.ui.adapter.HotCategoryAdapter
import com.xk.eyepetizer.ui.base.BaseFragment

/**
 * Created by xuekai on 2017/9/4.
 */
class HotDetailFragment : BaseFragment, HotContract.IHotCategoryView {

    var apiUrl = ""
    lateinit var presenter: HotCategoryPresenter

    val adapter by lazy { HotCategoryAdapter() }

    constructor() : super(-1)
    constructor(apiUrl: String) : this() {
        this.apiUrl = apiUrl
        presenter = HotCategoryPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val recyclerView = RecyclerView(context)
        recyclerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        return recyclerView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter.requestData(apiUrl)
    }

    override fun setListData(itemList: ArrayList<Item>) {
        adapter.addItemList(itemList)
    }

    override fun onError() {
        showToast("网络错误")
    }

}