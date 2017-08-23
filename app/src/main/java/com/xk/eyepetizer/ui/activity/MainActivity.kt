package com.xk.eyepetizer.ui.activity

import android.app.Fragment
import android.os.Bundle
import com.xk.eyepetizer.R
import com.xk.eyepetizer.ui.base.BaseActivity
import com.xk.eyepetizer.ui.fragment.FindFragment
import com.xk.eyepetizer.ui.fragment.HomeFragment
import com.xk.eyepetizer.ui.fragment.HotFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by xuekai on 2017/8/20.
 */

class MainActivity : BaseActivity() {
    val tabs = listOf(R.id.rb_home, R.id.rb_find, R.id.rb_hot)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRadio()
    }

    private fun setRadio() {
        rb_home.isChecked = true
        chooseFragment(R.id.rb_home)
        rg_root.setOnCheckedChangeListener { group, checkedId -> chooseFragment(checkedId) }
    }

    private fun chooseFragment(checkedId: Int) {
        val beginTransaction = fragmentManager.beginTransaction()

        val fragment: Fragment? = fragmentManager.findFragmentByTag(checkedId.toString())
        if (fragment == null) {
            when (checkedId) {
                R.id.rb_home -> beginTransaction.add(R.id.fl_content, HomeFragment(), checkedId.toString())
                R.id.rb_hot -> beginTransaction.add(R.id.fl_content, HotFragment(), checkedId.toString())
                R.id.rb_find -> beginTransaction.add(R.id.fl_content, FindFragment(), checkedId.toString())
            }
        }
        tabs.forEach { tab ->
            val beginTransaction = beginTransaction

            val fragment: Fragment? = fragmentManager.findFragmentByTag(tab.toString())

            if (tab == checkedId) {
                fragment?.let {
                    beginTransaction.show(it)
                }
            } else {
                fragment?.let {
                    beginTransaction.hide(it)
                }
            }
        }
        beginTransaction.commit()

    }

}
