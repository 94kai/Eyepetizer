package com.xk.eyepetizer.mvp.model

import com.xk.eyepetizer.io_main
import com.xk.eyepetizer.mvp.model.bean.Issue
import com.xk.eyepetizer.net.Network
import io.reactivex.Observable

/**
 * 详情页Model，请求默认精选（无date是banner，每次加载更多，加载一条带date）
 * Created by xuekai on 2017/8/25.
 */
class DetailModel {

    fun loadRelatedData(id:Long): Observable<Issue> {
        return Network.service.getRelatedData(id).io_main()
    }

    fun loadDetailMoreRelatedList(url:String): Observable<Issue> {
        return Network.service.getIssue(url).io_main()
    }

    fun loadReplyList(videoId:Long): Observable<Issue> {
        return Network.service.getReply(videoId).io_main()
    }
    fun loadMoreReplyList(url:String): Observable<Issue> {
        return Network.service.getIssue(url).io_main()
    }
}