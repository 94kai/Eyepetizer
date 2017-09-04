package com.xk.eyepetizer.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.contract.CategoryContract
import com.xk.eyepetizer.mvp.model.bean.Category
import com.xk.eyepetizer.mvp.presenter.CategoryPresenter
import com.xk.eyepetizer.showToast
import com.xk.eyepetizer.toActivityWithSerializable
import com.xk.eyepetizer.ui.activity.CategoryDetailActivity
import com.xk.eyepetizer.ui.adapter.CategoryAdapter
import com.xk.eyepetizer.ui.base.BaseFragment
import com.xk.eyepetizer.ui.base.tabsId
import com.xk.eyepetizer.util.DisplayManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_category.*
import java.util.*

/**
 * Created by xuekai on 2017/8/21.
 */
class CategoryFragment : BaseFragment(tabId = tabsId[1]), CategoryContract.IView {

    val categoryPresenter: CategoryPresenter

    val adapter by lazy {
        CategoryAdapter()
    }

    init {
        categoryPresenter = CategoryPresenter(this)
    }

    override fun showCategory(categorys: ArrayList<Category>) {
        adapter.setData(categorys)
    }


    override fun onError() {
        showToast("网络错误")
    }

    var isFirst = true
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_category, null)
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (gridLayoutManager.itemCount - 1 == position) {
                    return 2
                }
                return 1
            }

        }
        rv_category.layoutManager = gridLayoutManager
        rv_category.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        rv_category.adapter = adapter
        rv_category.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                val position = parent.getChildPosition(view)
                val offset = DisplayManager.dip2px(2f)!!

                outRect.set(if (position % 2 == 0) 0 else offset, offset,
                        if (position % 2 == 0) offset else 0, offset)
            }

        })
        adapter.onClick = { category -> activity.toActivityWithSerializable<CategoryDetailActivity>(category) }
        categoryPresenter.requestData()
    }

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            setupToolbar()
            isFirst = false
        }
    }

    override fun setupToolbar(): Boolean {
        if (super.setupToolbar()) {
            return true
        }
        super.setupToolbar()
        activity.toolbar.setBackgroundColor(0xddffffff.toInt())
        activity.iv_search.setImageResource(R.mipmap.ic_action_search)
        activity.tv_bar_title.setText("分类")
        return true
    }
}