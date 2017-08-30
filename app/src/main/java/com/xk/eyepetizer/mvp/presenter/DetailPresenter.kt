package com.xk.eyepetizer.mvp.presenter

import android.app.Activity
import com.xk.eyepetizer.dataFormat
import com.xk.eyepetizer.getNetType
import com.xk.eyepetizer.mvp.contract.DetailContract
import com.xk.eyepetizer.mvp.model.DetailModel
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.showToast
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

        val netType = (detailView as Activity).getNetType()
        val playInfo = itemData.data?.playInfo
        playInfo?.let {
            if (netType == 1) {
                //wifi
                //设置播放器
                for (playinfo in playInfo) {
                    if (playinfo.type == "high") {
                        val playUrl = playinfo.url
                        detailView.setPlayer(playUrl)
                        break
                    }
                }
            } else {
                //不是wifi，出提示
                for (playinfo in playInfo) {
                    if (playinfo.type == "normal") {
                        val playUrl = playinfo.url
                        detailView.setPlayer(playUrl)
                        (detailView as Activity).showToast("本次播放消耗${(detailView as Activity).dataFormat(playinfo.urlList[0].size)}流量")

                        break
                    }
                }
            }
        }


        //设置影片信息
        detailView.setMovieAuthorInfo(itemData)

        return requestRelatedData(itemData.data!!.id)
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