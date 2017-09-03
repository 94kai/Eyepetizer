package com.xk.eyepetizer.mvp.presenter

import com.xk.eyepetizer.mvp.contract.CategoryContract
import com.xk.eyepetizer.mvp.model.CategoryModel

/**
 * Created by xuekai on 2017/9/3.
 */
class CategoryPresenter(view: CategoryContract.IView) : CategoryContract.IPresenter {


    val categoryView: CategoryContract.IView
    val categoryModel: CategoryModel by lazy {
        CategoryModel()
    }


    init {
        categoryView = view
    }

    override fun requestData() {

        categoryModel.loadData()
                .subscribe({ categoryView.showCategory(it) }, {
                    it.printStackTrace()
                    categoryView.onError()
                })

    }

}