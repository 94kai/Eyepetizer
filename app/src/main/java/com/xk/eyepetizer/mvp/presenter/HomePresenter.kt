package com.xk.eyepetizer.mvp.presenter

import com.xk.eyepetizer.mvp.contract.HomeContract
import com.xk.eyepetizer.mvp.model.HomeModel
import com.xk.eyepetizer.mvp.model.bean.HomeBean

/**
 * Created by xuekai on 2017/8/21.
 */
class HomePresenter(view: HomeContract.IView) : HomeContract.IPresenter {
    val homeView: HomeContract.IView
    var nextPageUrl: String? = null
    val homeModel: HomeModel by lazy {
        HomeModel()
    }


    var bannerHomeBean: HomeBean? = null

    init {
        homeView = view
    }

    override fun start() {
    }

    override fun requestFirstData() {
        homeModel.loadFirstData()
                .flatMap({ homeBean ->
                    bannerHomeBean = homeBean

                    homeModel.loadMoreData(homeBean.nextPageUrl)
                })
                .subscribe({ homeBean ->
                    nextPageUrl = homeBean.nextPageUrl
                    bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size

                    bannerHomeBean?.issueList!![0].itemList.addAll(homeBean.issueList[0].itemList)
                    homeView.setFirstData(bannerHomeBean!!)
                }, { t ->
                    t.printStackTrace()
                    homeView.onError()
                })
    }

    override fun requestMoreData() {
        nextPageUrl?.let {
            homeModel.loadMoreData(it)
                    .subscribe({ homeBean ->
                        homeView.setMoreData(homeBean)
                        nextPageUrl = homeBean.nextPageUrl
                    })
        }
    }
}