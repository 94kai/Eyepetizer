package com.xk.eyepetizer.mvp.contract

import com.xk.eyepetizer.mvp.base.BasePresenter
import com.xk.eyepetizer.mvp.base.BaseView
import com.xk.eyepetizer.mvp.model.bean.HomeBean

/**
 * 首页的契约接口，统一管理view和presenter中的接口，使得二者的功能一目了然
 * Created by xuekai on 2017/8/21.
 */
interface HomeContract {
    interface IView : BaseView<IPresenter> {
        fun setFirstData(homeBean: HomeBean)
        fun setMoreData(homeBean: HomeBean)
        fun onError()
    }

    interface IPresenter : BasePresenter {
        fun requestFirstData()
        fun requestMoreData()
    }
}
