package com.xk.eyepetizer.net

import com.xk.eyepetizer.mvp.model.bean.HomeBean
import com.xk.eyepetizer.mvp.model.bean.Issue
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by xuekai on 2017/8/20.
 */

interface ApiService {

    /**
     * banner+一页数据，num=1
     */
    @GET("v2/feed?&num=1")
    fun getFirstHomeData(@Query("date") date: Long): Observable<HomeBean>

    /**
     * 根据nextpageurl请求数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>


    /**
     * issue里面包了itemlist和nextpageurl
     */
    @GET
    fun getIssue(@Url url: String): Observable<Issue>

    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Int): Observable<Issue>
}