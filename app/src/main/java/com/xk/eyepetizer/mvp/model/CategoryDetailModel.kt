package com.xk.eyepetizer.mvp.model

import com.xk.eyepetizer.io_main
import com.xk.eyepetizer.mvp.model.bean.Issue
import com.xk.eyepetizer.net.Network
import io.reactivex.Observable

/**
 * Created by xuekai on 2017/9/4.
 */
class CategoryDetailModel {

    fun loadData(id: Long): Observable<Issue> {
        return Network.service.getCategoryItemList(id).io_main()
    }

    fun loadMoreData(url: String): Observable<Issue> {
        return Network.service.getIssue(url).io_main()
    }
}