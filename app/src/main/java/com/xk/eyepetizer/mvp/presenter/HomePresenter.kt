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


    override fun requestFirstData() {
        homeModel.loadFirstData()
                .flatMap({ homeBean ->
                    //也可以在这里过滤掉banner2，不过在homebanner里做了过滤，就懒得改了
                    bannerHomeBean = homeBean
                    homeModel.loadMoreData(homeBean.nextPageUrl)
                })
                .subscribe({ homeBean ->
                    nextPageUrl = homeBean.nextPageUrl
                    bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size//这里记录轮播图的长度，在adapter中用

                    //过滤掉banner2item
                    val newItemList = homeBean.issueList[0].itemList
                    newItemList.filter { item -> item.type == "banner2" }.forEach { item -> newItemList.remove(item)  }

                    bannerHomeBean?.issueList!![0].itemList.addAll(newItemList)
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

                        //过滤掉banner2item
                        val newItemList = homeBean.issueList[0].itemList
                        newItemList.filter { item -> item.type == "banner2" }.forEach { item -> newItemList.remove(item)  }
                        homeView.setMoreData(newItemList)
                        nextPageUrl = homeBean.nextPageUrl
                    })
        }
    }

}