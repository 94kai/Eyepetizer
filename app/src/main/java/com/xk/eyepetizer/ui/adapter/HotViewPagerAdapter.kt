package com.xk.eyepetizer.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by xuekai on 2017/9/4.
 */
class HotViewPagerAdapter : FragmentPagerAdapter {


    var titleList: ArrayList<String>? = null
    var fragmentList: ArrayList<Fragment>? = null

    constructor(supportFragmentManager:FragmentManager,titleList: ArrayList<String>?, fragmentList: ArrayList<Fragment>?) : super(supportFragmentManager) {
        this.titleList = titleList
        this.fragmentList = fragmentList
    }



    override fun getCount(): Int = if (fragmentList != null) fragmentList!!.size else 0

    override fun getPageTitle(position: Int): CharSequence {
        return titleList!![position]
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]

    }


}