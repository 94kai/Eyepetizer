package com.xk.eyepetizer.mvp.contract

import com.xk.eyepetizer.mvp.base.BasePresenter
import com.xk.eyepetizer.mvp.base.BaseView
import com.xk.eyepetizer.mvp.model.bean.Category
import com.xk.eyepetizer.mvp.model.bean.Item

/**
 * 分类详情的契约接口，统一管理view和presenter中的接口，使得二者的功能一目了然
 * Created by xuekai on 2017/9/4.
 */
interface CategoryDetailContract {
    interface IView : BaseView<IPresenter> {
        fun setHeader(category: Category)
        fun setListData(itemList: ArrayList<Item>)
        fun onError()
    }

    interface IPresenter : BasePresenter {
        fun requestMoreData()

        fun start(category: Category)
    }
}