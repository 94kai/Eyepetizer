package com.xk.eyepetizer.mvp.presenter

import com.xk.eyepetizer.mvp.contract.HomeContract
import com.xk.eyepetizer.mvp.model.HomeModel

/**
 * Created by xuekai on 2017/8/21.
 */
class HomePresenter(view: HomeContract.IView) : HomeContract.IPresenter {
    val homeView: HomeContract.IView
    var nextPageUrl: String? = null
    val homeModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        homeView = view
    }

    override fun start() {
    }

    override fun requestFirstData() {
        homeModel.loadFirstData()
                .flatMap({ homeBean ->
                    homeView.setFirstData(homeBean)
                    homeModel.loadMoreData(homeBean.nextPageUrl)
                })
                .subscribe({ homeBean ->
                    homeView.setMoreData(homeBean)
                    nextPageUrl = homeBean.nextPageUrl
                }, { t -> t.printStackTrace() })
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