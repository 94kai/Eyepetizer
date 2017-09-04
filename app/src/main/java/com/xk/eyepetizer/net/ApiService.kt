package com.xk.eyepetizer.net

import com.xk.eyepetizer.mvp.model.bean.Category
import com.xk.eyepetizer.mvp.model.bean.HomeBean
import com.xk.eyepetizer.mvp.model.bean.HotCategory
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
     * 热门的类别
     */
    @GET
    fun getHotCategory(@Url url: String): Observable<HotCategory>


    /**
     * 获取回复
     */
    @GET("v2/replies/video?")
    fun getReply(@Query("videoId") videoId: Long): Observable<Issue>

    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<Issue>

    /**
     * 获取分类
     */
    @GET("v4/categories")
    fun getCategory(): Observable<ArrayList<Category>>

    /**
     * 获取分类下的全部数据
     */
    @GET("v4/categories/videoList")
    fun getCategoryItemList(@Query("id") id: Long): Observable<Issue>
}
