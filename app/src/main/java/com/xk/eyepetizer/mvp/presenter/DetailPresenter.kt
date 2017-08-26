package com.xk.eyepetizer.mvp.presenter

import com.xk.eyepetizer.mvp.contract.DetailContract
import com.xk.eyepetizer.mvp.model.DetailModel
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.util.DisplayManager

/**
 * Created by xuekai on 2017/8/25.
 */
class DetailPresenter(view: DetailContract.IView,val itemData: Item) : DetailContract.IPresenter {


    val detailView: DetailContract.IView
    val detailModel: DetailModel by lazy {
        DetailModel()
    }



    init {
        detailView = view
    }

    override fun requestBasicDataFromMemory() {
        //设置背景
        val url = itemData.data?.cover?.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        detailView.setBackground(url)

        //设置播放器
        // TODO: by xk 2017/8/25 15:02 wifi 播放高清、流量播放标清
        val playUrl= itemData.data?.playInfo!![0].url
        detailView.setPlayer(playUrl)

        //设置影片信息
        detailView.setMovieAuthorInfo(itemData)




        requestRelatedData(itemData.data.id)
    }


    override fun requestRelatedData(id: Int) {
            detailModel.loadRelatedData(id)
                    .subscribe({issue ->  detailView.setRelated(issue.itemList)})
    }


}