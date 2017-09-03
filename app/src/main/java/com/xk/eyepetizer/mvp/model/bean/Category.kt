package com.xk.eyepetizer.mvp.model.bean

import java.io.Serializable

/**
 * Created by xuekai on 2017/9/3.
 */
data class Category(val id: Long, val name: String, val description: String, val bgPicture: String, val bgColor: String, val headerImage: String) : Serializable
