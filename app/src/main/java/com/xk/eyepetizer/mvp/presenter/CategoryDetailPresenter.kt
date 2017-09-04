package com.xk.eyepetizer.mvp.presenter

import com.xk.eyepetizer.mvp.contract.CategoryDetailContract
import com.xk.eyepetizer.mvp.model.CategoryDetailModel
import com.xk.eyepetizer.mvp.model.bean.Category

/**
 * Created by xuekai on 2017/9/4.
 */
class CategoryDetailPresenter(view: CategoryDetailContract.IView) : CategoryDetailContract.IPresenter {


    val categoryView: CategoryDetailContract.IView

    var nextPageUrl = ""

    init {
        categoryView = view
    }

    override fun requestMoreData() {
        categoryDetailModel.loadMoreData(nextPageUrl)
                .subscribe({ issue ->
                    nextPageUrl = issue.nextPageUrl
                    categoryView.setListData(issue.itemList)
                }, {
                    it.printStackTrace()
                    categoryView.onError()
                })
    }

    override fun start(category: Category) {
        categoryView.setHeader(category)
        categoryDetailModel.loadData(category.id)
                .subscribe({ issue ->
                    nextPageUrl = issue.nextPageUrl
                    categoryView.setListData(issue.itemList)
                }, {
                    it.printStackTrace()
                    categoryView.onError()
                })
    }


    val categoryDetailModel: CategoryDetailModel by lazy {
        CategoryDetailModel()
    }

}