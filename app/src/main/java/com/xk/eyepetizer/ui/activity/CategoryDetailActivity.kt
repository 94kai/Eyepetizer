package com.xk.eyepetizer.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.xk.eyepetizer.R
import com.xk.eyepetizer.TAG
import com.xk.eyepetizer.mvp.contract.CategoryDetailContract
import com.xk.eyepetizer.mvp.model.bean.Category
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.mvp.presenter.CategoryDetailPresenter
import com.xk.eyepetizer.showToast
import com.xk.eyepetizer.ui.adapter.CategoryDetailAdapter
import com.xk.eyepetizer.ui.base.BaseActivity
import com.xk.eyepetizer.util.DisplayManager
import kotlinx.android.synthetic.main.activity_category_detail.*

/**
 * Created by xuekai on 2017/9/3.
 */
class CategoryDetailActivity : BaseActivity(), CategoryDetailContract.IView {

    val adapter by lazy { CategoryDetailAdapter() }
    lateinit var categoryDetailPresenter: CategoryDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val category = intent.getSerializableExtra("data") as Category
        setContentView(R.layout.activity_category_detail)
        categoryDetailPresenter = CategoryDetailPresenter(this)
        initView()
        categoryDetailPresenter.start(category)
    }

    fun initView() {
        rv_category_detail.layoutManager = LinearLayoutManager(this)
        rv_category_detail.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        rv_category_detail.adapter = adapter
        rv_category_detail.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                val position = parent.getChildPosition(view)
                val offset = DisplayManager.dip2px(10f)!!
                if ((position == 0)) {
                    outRect.set(0, offset, 0, 0)
                }
            }
        })


        rv_category_detail.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = rv_category_detail.getChildCount()
                    val itemCount = rv_category_detail.layoutManager.getItemCount()
                    val firstVisibleItem = (rv_category_detail.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        Log.d(TAG, "到底了");
                        if (!loadingMore) {
                            loadingMore = true
                            onLoadMore()
                        }
                    }
                }
            }
        })
    }

    var loadingMore = false
    fun onLoadMore() {
        categoryDetailPresenter.requestMoreData()
    }

    override fun setHeader(category: Category) {
        category_header.setData(category)
    }

    override fun setListData(itemList: ArrayList<Item>) {
        loadingMore = false
        adapter.addData(itemList)
    }

    override fun onError() {
        showToast("网络错误")
    }

}