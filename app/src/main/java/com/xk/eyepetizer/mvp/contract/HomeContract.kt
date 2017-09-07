package com.xk.eyepetizer.mvp.contract

import com.xk.eyepetizer.mvp.base.BasePresenter
import com.xk.eyepetizer.mvp.base.BaseView
import com.xk.eyepetizer.mvp.model.bean.HomeBean
import com.xk.eyepetizer.mvp.model.bean.Item

/**
 * 首页的契约接口，统一管理view和presenter中的接口，使得二者的功能一目了然
 * Created by xuekai on 2017/8/21.
 */
interface HomeContract {

    interface IView : BaseView<IPresenter> {
        fun setFirstData(homeBean: HomeBean)
        fun setMoreData(itemList:ArrayList<Item>)
        fun onError()
    }




    interface IPresenter : BasePresenter {
        /**
         * 刷新数据、第一次请求你数据
         */
        fun requestFirstData()

        /**
         * 底部加载更多
         */
        fun requestMoreData()
    }
}
