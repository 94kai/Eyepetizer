package com.xk.eyepetizer.ui.view.home.banner

import android.support.v4.view.ViewPager
import android.view.View

/**
 * Created by xuekai on 2017/8/22.
 */
class HomeBannerTransformer : ViewPager.PageTransformer {
    override fun transformPage(page: View?, position: Float) {
        val width: Int = page?.width!!
        //以向左滑动为例
//        if (position <= 0) {//中间的
        page.scrollX = (position * width).toInt() / 4 * 3
//        } else if (position <= 1) {//右侧的
//            page?.scrollX = (position * width).toInt() / 4 * 3

//        }
    }

}
