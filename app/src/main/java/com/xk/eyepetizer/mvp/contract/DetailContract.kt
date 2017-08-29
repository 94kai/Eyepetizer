package com.xk.eyepetizer.mvp.contract

import com.xk.eyepetizer.mvp.base.BasePresenter
import com.xk.eyepetizer.mvp.base.BaseView
import com.xk.eyepetizer.mvp.model.bean.Issue
import com.xk.eyepetizer.mvp.model.bean.Item
import io.reactivex.disposables.Disposable

/**
 * 详情页的契约接口，统一管理view和presenter中的接口，使得二者的功能一目了然
 * Created by xuekai on 2017/8/25.
 */
interface DetailContract {
    interface IView : BaseView<IPresenter> {
        /**
         * 设置播放器
         */
        fun setPlayer(playUrl: String)

        /**
         * 设置影片信息/作者信息
         */
        fun setMovieAuthorInfo(info: Item)


        /**
         * 设置相关视频
         */
        fun setRelated(items: ArrayList<Item>)

        fun setBackground(url: String)

        /**
         * 显示相关推荐之类的全部的view
         */
        fun showDropDownView(title: String)

        /**
         * 设置相关推荐之类的全部的view的数据
         */
        fun setDropDownView(issue: Issue)

        /**
         * 设置相关推荐之类的全部的view的数据(底部加载更多)
         */
        fun setMoreDropDownView(issue: Issue?)


    }

    interface IPresenter : BasePresenter {
        /**
         * 请求相关视频数据
         */
        fun requestRelatedData(id: Long): Disposable?

        /**
         * 从内存中获取基础数据（影片信息、作者信息等）
         */
        fun requestBasicDataFromMemory(itemData: Item): Disposable?

        /**
         * 请求相关推荐之类的数据
         */
        fun requestRelatedAllList(url: String?, title: String): Disposable?

        /**
         * 请求相关推荐之类的数据的更多数据
         */
        fun requestRelatedAllMoreList(): Disposable?

        /**
         * 请求评论数据
         */
        fun requestReply(videoId: Long): Disposable?

        /**
         * 请求更多评论数据
         */
        fun requestMoreReply(): Disposable?

    }
}