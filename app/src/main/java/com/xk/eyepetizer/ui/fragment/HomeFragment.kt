package com.xk.eyepetizer.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xk.eyepetizer.R
import com.xk.eyepetizer.TAG
import com.xk.eyepetizer.mvp.contract.HomeContract
import com.xk.eyepetizer.mvp.model.bean.HomeBean
import com.xk.eyepetizer.mvp.presenter.HomePresenter
import com.xk.eyepetizer.showToast
import com.xk.eyepetizer.ui.adapter.HomeAdapter
import com.xk.eyepetizer.ui.base.BaseFragment
import com.xk.eyepetizer.ui.view.PullRecyclerView
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by xuekai on 2017/8/21.
 */
class HomeFragment : BaseFragment(), HomeContract.IView {


    val homeAdapter: HomeAdapter by lazy { HomeAdapter() }

    lateinit var presenter: HomePresenter
    override fun setPresenter(presenter: HomeContract.IPresenter) {
        this.presenter = presenter as HomePresenter
    }

    init {
        setPresenter(HomePresenter(this))
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, null)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        presenter.requestFirstData()

    }

    var loadingMore = false
    private fun initView() {
        rv_home.adapter = homeAdapter
        rv_home.layoutManager = LinearLayoutManager(activity)
        rv_home.setOnRefreshListener(object :PullRecyclerView.OnRefreshListener{
            override fun onRefresh() {
                presenter.requestFirstData()
            }
        })

        rv_home.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    rv_home.adapter.ge

                    val childCount = rv_home.getChildCount()
                    val itemCount = rv_home.layoutManager.getItemCount()
                    val firstVisibleItem = (rv_home.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem+childCount==itemCount) {
                        Log.d(TAG, "到底了" );
                        if (!loadingMore) {
                            loadingMore = true
                            onLoadMore()
                        }
                    }

                }
            }
        })
    }

    fun onLoadMore() {
        presenter.requestMoreData()
    }


    override fun setMoreData(homeBean: HomeBean) {
        loadingMore = false
        homeAdapter.addData(homeBean)

    }

    override fun setFirstData(homeBean: HomeBean) {
        homeAdapter.setBannerSize(homeBean.issueList[0].count)
        homeAdapter.itemList = homeBean.issueList[0].itemList
        rv_home.hideLoading()
    }

    override fun onError() {
        showToast("网络错误")
        rv_home.hideLoading()
    }
}