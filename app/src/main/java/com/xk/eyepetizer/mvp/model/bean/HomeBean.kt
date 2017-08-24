package com.xk.eyepetizer.mvp.model.bean

/**
 * Created by xuekai on 2017/8/20.
 */
data class HomeBean(var issueList: ArrayList<Issue>, val nextPageUrl: String, val nextPublishTime: Long, val newestIssueType: String, val dialog: Any)


//    "issueList": [],
//    "nextPageUrl": "http://baobab.kaiyanapp.com/api/v2/feed?date=1503104400000&num=1",
//    "nextPublishTime": 1503277200000,
//    "newestIssueType": "morning",
//    "dialog": null