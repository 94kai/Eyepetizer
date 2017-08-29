package com.xk.eyepetizer.mvp.model.bean

/**
 * Created by xuekai on 2017/8/20.
 */
data class Issue(val releaseTime:Long,val type:String,val date:Long,val total:Int,val publishTime:Long,val itemList:ArrayList<Item>,var count:Int,val nextPageUrl:String)
//    "releaseTime": 1503190800000,
//    "type": "morning",
//    "date": 1503190800000,
//    "publishTime": 1503190800000,
//    "itemList": [],
//    "count": 5
