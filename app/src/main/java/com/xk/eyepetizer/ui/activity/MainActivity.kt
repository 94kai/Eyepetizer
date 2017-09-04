package com.xk.eyepetizer.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.xk.eyepetizer.R
import com.xk.eyepetizer.ui.base.BaseActivity
import com.xk.eyepetizer.ui.base.BaseFragment
import com.xk.eyepetizer.ui.base.currentFragment
import com.xk.eyepetizer.ui.base.tabsId
import com.xk.eyepetizer.ui.fragment.CategoryFragment
import com.xk.eyepetizer.ui.fragment.HomeFragment
import com.xk.eyepetizer.ui.fragment.HotFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by xuekai on 2017/8/20.
 */

class MainActivity : BaseActivity() {
    // TODO: by xk 2017/8/25 23:30 启动页视频 在assets目录

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRadio()
    }

    private fun setRadio() {
        rb_home.isChecked = true
        chooseFragment(R.id.rb_home)
        rg_root.setOnCheckedChangeListener { _, checkedId -> chooseFragment(checkedId) }
    }

    private fun chooseFragment(checkedId: Int) {
        currentFragment = checkedId

        val beginTransaction = supportFragmentManager.beginTransaction()

        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(checkedId.toString())
        if (fragment == null) {
            when (checkedId) {
                R.id.rb_home -> beginTransaction.add(R.id.fl_content, HomeFragment(), checkedId.toString())
                R.id.rb_hot -> beginTransaction.add(R.id.fl_content, HotFragment(), checkedId.toString())
                R.id.rb_category -> beginTransaction.add(R.id.fl_content, CategoryFragment(), checkedId.toString())
            }
        }
        tabsId.forEach { tab ->

            val aFragment = supportFragmentManager.findFragmentByTag(tab.toString()) as BaseFragment?

            if (tab == checkedId) {
//                aFragment.currentFragment=aFragment::class.java
                aFragment?.let {
                    aFragment.setupToolbar()
                    beginTransaction.show(it)
                }
            } else {
                aFragment?.let {
                    beginTransaction.hide(it)
                }
            }
        }


        beginTransaction.commit()

    }

}
