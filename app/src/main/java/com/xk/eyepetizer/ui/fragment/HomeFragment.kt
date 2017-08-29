package com.xk.eyepetizer.ui.fragment

import android.graphics.Typeface
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
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.mvp.presenter.HomePresenter
import com.xk.eyepetizer.showToast
import com.xk.eyepetizer.ui.adapter.HomeAdapter
import com.xk.eyepetizer.ui.base.BaseFragment
import com.xk.eyepetizer.ui.view.home.PullRecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by xuekai on 2017/8/21.
 */
class HomeFragment : BaseFragment(), HomeContract.IView {

    val simpleDateFormat by lazy { SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH) }

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


        activity.tv_bar_title?.typeface = Typeface.createFromAsset(activity?.assets, "fonts/Lobster-1.4.otf")
        val paint = activity.tv_bar_title.paint
        paint.setFakeBoldText(true)


        rv_home.adapter = homeAdapter
        rv_home.layoutManager = LinearLayoutManager(activity)
        rv_home.setOnRefreshListener(object : PullRecyclerView.OnRefreshListener {
            override fun onRefresh() {
                presenter.requestFirstData()
            }
        })

        rv_home.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = rv_home.getChildCount()
                    val itemCount = rv_home.layoutManager.getItemCount()
                    val firstVisibleItem = (rv_home.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        Log.d(TAG, "到底了");
                        if (!loadingMore) {
                            loadingMore = true
                            onLoadMore()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                setupToolbar()
            }
        })
    }


    val linearLayoutManager by lazy {
        rv_home.layoutManager as LinearLayoutManager
    }

    /**
     * recyclerview滚动的时候会调用这里，在这里设置toolbar
     */
    private fun setupToolbar() {
        val findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
        if (findFirstVisibleItemPosition == 0) {//设置为透明
            activity.toolbar.setBackgroundColor(0x00000000)
            activity.iv_search.setImageResource(R.mipmap.ic_action_search_white)
            activity.tv_bar_title.setText("")

        } else {
            activity.toolbar.setBackgroundColor(0xddffffff.toInt())
            activity.iv_search.setImageResource(R.mipmap.ic_action_search)
            val itemList = homeAdapter.itemList
            val item = itemList[findFirstVisibleItemPosition + homeAdapter.bannerItemListCount - 1]
            if(item.type=="textHeader"){
                activity.tv_bar_title.setText(item.data?.text)
            }else{
                activity.tv_bar_title.setText(simpleDateFormat.format(item.data?.date))
            }
        }
    }

    fun onLoadMore() {
        presenter.requestMoreData()
    }


    override fun setMoreData(itemList: ArrayList<Item>) {
        loadingMore = false
        homeAdapter.addData(itemList)
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

