package com.xk.eyepetizer.mvp.contract

import com.xk.eyepetizer.mvp.base.BasePresenter
import com.xk.eyepetizer.mvp.base.BaseView
import com.xk.eyepetizer.mvp.model.bean.HotCategory
import com.xk.eyepetizer.mvp.model.bean.Item

/**
 * 热门的契约接口，统一管理view和presenter中的接口，使得二者的功能一目了然
 * Created by xuekai on 2017/9/4.
 */
interface HotContract {
    interface IHotFragmentView : BaseView<IHotFragmentPresenter> {
        fun setTabAndFragment(hotCategory: HotCategory)
        fun onError()
    }

    interface IHotCategoryView : BaseView<IHotCategoryPresenter> {
        fun setListData(itemList:ArrayList<Item>)
        fun onError()
    }

    interface IHotFragmentPresenter : BasePresenter {
        fun requestHotCategory()
    }

    interface IHotCategoryPresenter : BasePresenter {
        fun requestData(url: String)
    }
}