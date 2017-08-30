package com.xk.eyepetizer.ui.view.home.banner

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.xk.eyepetizer.R
import com.xk.eyepetizer.mvp.model.bean.Item
import com.xk.eyepetizer.ui.view.common.JumpShowTextView
import com.xk.eyepetizer.util.DisplayManager


/**
 * Created by xuekai on 2017/8/21.
 */
class HomeBanner : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
        initListener()
    }

    fun setData(itemList:ArrayList<Item>){
            itemList.filter { item -> item.type == "banner2" }.forEach { item -> itemList.remove(item) }
            bannerAdapter.datas = itemList
            bannerAdapter.notifyDataSetChanged()
            setIndicators(itemList)
            setTitleSlogan(0)

    }

    private val bannerAdapter: BannerAdapter by lazy { BannerAdapter() }
     val viewPager: ViewPager by lazy { ViewPager(context) }
    private val indicators: LinearLayout by lazy { LinearLayout(context) }
    private val tvTitle: JumpShowTextView by lazy { JumpShowTextView(context) }
    private val tvSlogan: JumpShowTextView by lazy { JumpShowTextView(context) }


    private fun initListener() {
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {

                setTitleSlogan(position)

                for (i in 0..indicators.childCount - 1) {
                    if (i == position) {
                        (indicators.getChildAt(i) as Indicator).setState(true)
                    } else {
                        (indicators.getChildAt(i) as Indicator).setState(false)
                    }
                }
            }

        })

    }

    private var currentTitlePostion=0

    private fun setTitleSlogan(position: Int) {
        currentTitlePostion=position
        val bannerItemData = bannerAdapter.datas!![position]
        tvTitle.text = bannerItemData.data?.title
        tvSlogan.text = bannerItemData.data?.slogan
    }


    private fun initView() {


        viewPager.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, DisplayManager.getRealHeight(810)!!)
        viewPager.adapter = bannerAdapter
        viewPager.setPageTransformer(true, HomeBannerTransformer())

        val floatInfo = LinearLayout(context)
        floatInfo.gravity = Gravity.CENTER_HORIZONTAL
        floatInfo.orientation = LinearLayout.VERTICAL
        val floatInfoParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        floatInfoParams.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        floatInfo.layoutParams = floatInfoParams
        val homePageHeaderIcon = ImageView(context)
        homePageHeaderIcon.setImageResource(R.mipmap.home_page_header_icon)
        homePageHeaderIcon.scaleType = ImageView.ScaleType.CENTER_INSIDE
        homePageHeaderIcon.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayManager.getRealHeight(110)!!)

        tvTitle.textSize = 52f
        tvTitle.color = Color.WHITE
        tvTitle.isBold = true
        tvTitle.marginBottom=DisplayManager.dip2px(5f)?.toFloat()!!
        tvTitle.layoutParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)

        tvSlogan.textSize = 33f
        tvSlogan.color = Color.WHITE
        tvSlogan.layoutParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)


        indicators.gravity = Gravity.CENTER_HORIZONTAL
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayManager.getRealHeight(20)!!)
        layoutParams.topMargin = DisplayManager.getRealHeight(30)!!
        layoutParams.bottomMargin = DisplayManager.getRealHeight(28)!!

        indicators.layoutParams = layoutParams
        indicators.orientation = LinearLayout.HORIZONTAL


        floatInfo.addView(homePageHeaderIcon)
        floatInfo.addView(tvTitle)
        floatInfo.addView(tvSlogan)
        floatInfo.addView(indicators)


        addView(viewPager)
        addView(floatInfo)

    }


    fun setIndicators(bannerDatas: ArrayList<Item>) {
        indicators.removeAllViews()
        bannerDatas.forEach { _ ->
            val indicator = Indicator(context)
            val layoutParams = LinearLayout.LayoutParams(DisplayManager.getRealHeight(20)!!, DisplayManager.getRealHeight(20)!!)
            layoutParams.leftMargin = DisplayManager.getRealWidth(10)!!
            layoutParams.rightMargin = DisplayManager.getRealWidth(10)!!
            indicator.layoutParams = layoutParams


            indicators.addView(indicator)
        }
        (indicators.getChildAt(0) as Indicator).setState(true)
    }

}