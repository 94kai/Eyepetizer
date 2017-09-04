package com.xk.eyepetizer.mvp.presenter

import com.xk.eyepetizer.mvp.contract.HotContract
import com.xk.eyepetizer.mvp.model.HotModel

/**
 * 周排行、日排行。。。
 * Created by xuekai on 2017/9/4.
 */
class HotCategoryPresenter(view: HotContract.IHotCategoryView) : HotContract.IHotCategoryPresenter {
    override fun requestData(url: String) {
        hotModel.loadListData(url)
                .subscribe({ issue ->
                    hotCategoryView.setListData(issue.itemList)
                }, {
                    it.printStackTrace()
                    hotCategoryView.onError()
                })
    }


    val hotCategoryView: HotContract.IHotCategoryView

    init {
        hotCategoryView = view
    }

    val hotModel: HotModel by lazy {
        HotModel()
    }

}