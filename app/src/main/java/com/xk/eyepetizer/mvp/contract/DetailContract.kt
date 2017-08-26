package com.xk.eyepetizer.mvp.contract

import com.xk.eyepetizer.mvp.base.BasePresenter
import com.xk.eyepetizer.mvp.base.BaseView
import com.xk.eyepetizer.mvp.model.bean.Item

/**
 * 详情页的契约接口，统一管理view和presenter中的接口，使得二者的功能一目了然
 * Created by xuekai on 2017/8/25.
 */
interface DetailContract {
    interface IView : BaseView<IPresenter> {
        /**
         * 设置播放器
         */
        fun setPlayer(playUrl:String)

        /**
         * 设置影片信息/作者信息
         */
        fun setMovieAuthorInfo(info: Item)


        /**
         * 设置相关视频
         */
        fun setRelated(items:ArrayList<Item>)

        fun setBackground(url:String)
    }

    interface IPresenter : BasePresenter {
        /**
         * 请求相关视频数据
         */
        fun requestRelatedData(id: Int)

        /**
         * 从内存中获取基础数据（影片信息、作者信息等）
         */
        fun requestBasicDataFromMemory()
    }
}