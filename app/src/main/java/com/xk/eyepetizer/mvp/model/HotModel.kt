package com.xk.eyepetizer.mvp.model

import com.xk.eyepetizer.io_main
import com.xk.eyepetizer.mvp.model.bean.HotCategory
import com.xk.eyepetizer.mvp.model.bean.Issue
import com.xk.eyepetizer.net.Network
import io.reactivex.Observable

/**
 * 热门Model
 * Created by xuekai on 2017/9/4.
 */
class HotModel {

    fun loadListData(url: String): Observable<Issue> {
        return Network.service.getIssue(url).io_main()
    }

    fun loadCategoryData(url: String): Observable<HotCategory> {
        return Network.service.getHotCategory(url).io_main()
    }
}