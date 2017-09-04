package com.xk.eyepetizer.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.contract.HotContract
import com.xk.eyepetizer.mvp.model.bean.HotCategory
import com.xk.eyepetizer.mvp.presenter.HotFragmentPresenter
import com.xk.eyepetizer.showToast
import com.xk.eyepetizer.ui.adapter.HotViewPagerAdapter
import com.xk.eyepetizer.ui.base.BaseFragment
import com.xk.eyepetizer.ui.base.tabsId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Created by xuekai on 2017/8/21.
 */
class HotFragment : BaseFragment(tabId = tabsId[2]), HotContract.IHotFragmentView {
    override fun setTabAndFragment(hotCategory: HotCategory) {

        val titleList = ArrayList<String>()
        for (tab in hotCategory.tabInfo.tabList) {
            titleList.add(tab.name)
        }
        val fragmentList = ArrayList<Fragment>()
        for (tab in hotCategory.tabInfo.tabList) {
            fragmentList.add(HotDetailFragment(tab.apiUrl))
        }
        val hotViewPagerAdapter = HotViewPagerAdapter(fragmentManager, titleList, fragmentList)
        vpMain.setAdapter(hotViewPagerAdapter)
        tablayout.setupWithViewPager(vpMain)

        ViewUtil.setUpIndicatorWidth(tablayout)
    }

    val presenter: HotFragmentPresenter

    init {
        presenter = HotFragmentPresenter(this)
    }


    override fun onError() {
        showToast("网络错误")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_hot, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter.requestHotCategory()

    }

    override fun setupToolbar(): Boolean {
        if (super.setupToolbar()) {
            return true
        }
        super.setupToolbar()
        activity.toolbar.setBackgroundColor(0xddffffff.toInt())
        activity.iv_search.setImageResource(R.mipmap.ic_action_search)
        activity.tv_bar_title.setText("热门")
        return true
    }

    var isFirst = true
    override fun onResume() {
        super.onResume()
        if (isFirst) {
            setupToolbar()
            isFirst = false
        }
    }


}