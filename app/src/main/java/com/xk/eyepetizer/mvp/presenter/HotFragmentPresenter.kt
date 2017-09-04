package com.xk.eyepetizer.mvp.presenter

import android.util.Log
import com.xk.eyepetizer.mvp.contract.HotContract
import com.xk.eyepetizer.mvp.model.HotModel

/**
 * 首页第三个tab
 * Created by xuekai on 2017/9/4.
 */
class HotFragmentPresenter(view: HotContract.IHotFragmentView) : HotContract.IHotFragmentPresenter {
    override fun requestHotCategory() {
        hotModel.loadCategoryData("http://baobab.kaiyanapp.com/api/v4/rankList")
                .subscribe({ hotCategory ->
                    Log.i("HotFragmentPresenter", "requestHotCategory-->${hotCategory}")
                    hotFragmentView.setTabAndFragment(hotCategory)
                }, {
                    it.printStackTrace()
                    hotFragmentView.onError()
                })
    }

    val hotFragmentView: HotContract.IHotFragmentView

    init {
        hotFragmentView = view
    }


    val hotModel: HotModel by lazy {
        HotModel()
    }

}