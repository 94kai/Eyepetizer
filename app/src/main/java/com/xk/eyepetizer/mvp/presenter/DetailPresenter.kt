package com.xk.eyepetizer.mvp.presenter

import com.xk.eyepetizer.mvp.contract.DetailContract
import com.xk.eyepetizer.mvp.model.DetailModel
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.util.DisplayManager
import io.reactivex.disposables.Disposable

/**
 * Created by xuekai on 2017/8/25.
 */
class DetailPresenter(view: DetailContract.IView) : DetailContract.IPresenter {


    var moreReatedUrl: String? = ""
    var moreReplyUrl: String? = ""

    val detailView: DetailContract.IView
    val detailModel: DetailModel by lazy {
        DetailModel()
    }


    init {
        detailView = view
    }

    override fun requestBasicDataFromMemory(itemData: Item): Disposable? {
        //设置背景
        val url = itemData.data?.cover?.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        detailView.setBackground(url)
        //设置播放器
        // TODO: by xk 2017/8/25 15:02 wifi 播放高清、流量播放标清
        val playUrl = itemData.data?.playInfo!![0].url
        detailView.setPlayer(playUrl)
        //设置影片信息
        detailView.setMovieAuthorInfo(itemData)

        return requestRelatedData(itemData.data.id)
    }

    override fun requestRelatedData(id: Long): Disposable? {
        return detailModel.loadRelatedData(id)
                .subscribe({ issue -> detailView.setRelated(issue.itemList) })
    }

    override fun requestRelatedAllList(url: String?, title: String): Disposable? {
        detailView.showDropDownView(title)
        url?.let {
            return detailModel.loadDetailMoreRelatedList(url)
                    .subscribe({ issue ->
                        moreReatedUrl = issue.nextPageUrl
                        detailView.setDropDownView(issue)
                    })
        }
        return null
    }

    override fun requestRelatedAllMoreList(): Disposable? {
        moreReatedUrl?.let {
            if (it != "") {
                return detailModel.loadDetailMoreRelatedList(it)
                        .subscribe({ issue ->
                            moreReatedUrl = issue.nextPageUrl
                            detailView.setMoreDropDownView(issue)
                        })
            }
        }
        detailView.setMoreDropDownView(null)
        return null
    }

    override fun requestReply(videoId: Long): Disposable? {
        detailView.showDropDownView("评论")
        return detailModel.loadReplyList(videoId)
                .subscribe({ issue ->
                    moreReplyUrl = issue.nextPageUrl
                    detailView.setDropDownView(issue)
                })
    }

    override fun requestMoreReply(): Disposable? {
        moreReplyUrl?.let {
            if (it != "") {
                return detailModel.loadMoreReplyList(it)
                        .subscribe({ issue ->
                            moreReplyUrl = issue.nextPageUrl
                            detailView.setMoreDropDownView(issue)
                        })
            }
        }
        detailView.setMoreDropDownView(null)
        return null
    }

}