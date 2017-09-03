package com.xk.eyepetizer.mvp.model

import com.xk.eyepetizer.io_main
import com.xk.eyepetizer.mvp.model.bean.Category
import com.xk.eyepetizer.net.Network
import io.reactivex.Observable

/**
 * Created by xuekai on 2017/9/3.
 */
class CategoryModel {

    fun loadData(): Observable<ArrayList<Category>> {
        return Network.service.getCategory().io_main()
    }
}