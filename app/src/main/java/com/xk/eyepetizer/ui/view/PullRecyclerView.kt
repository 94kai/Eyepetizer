package com.xk.eyepetizer.ui.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.xk.eyepetizer.R
import com.xk.eyepetizer.ui.view.banner.HomeBanner

/**
 * Created by xuekai on 2017/8/23.
 */
class PullRecyclerView : RecyclerView {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {

    }

    var originalFirstItemHeight = 0
    var originalFirstItemWeight = 0
    var downY = -1
    var constDownY = -1
    var canRefresh = false
    var isFirstMove = true
    var tempWidth = -1
    var dx = 0

    override fun onTouchEvent(e: MotionEvent?): Boolean {

        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                downY = e.y.toInt()
                constDownY = e.y.toInt()
                if (!canScrollVertically(-1)) {
                    canRefresh = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isFirstMove) {
                    isFirstMove = false
                    if (canRefresh) {
                        if (e.y - downY > 0) {
                            canRefresh = true
                        } else {
                            canRefresh = false
                        }
                    }

                }
                if (canRefresh) {
                    if (getChildAt(0) is HomeBanner) {

                        val firstView = getChildAt(0) as HomeBanner
                        if (!hasShow) {
                            showLoading(firstView)
                        }


                        val fl = e.y - constDownY//fl从1-500   缩放比例从0-1
                        var fl1 = fl / 500f
                        if (fl1 > 1) {
                            fl1=1f

                        }
                        loading.scaleX = fl1
                        loading.scaleY = fl1

                        val layoutParams = firstView.layoutParams
                        if (layoutParams.height < 0 || tempWidth < 0) {
                            originalFirstItemHeight = getChildViewHolder(firstView).itemView.height
                            originalFirstItemWeight = getChildViewHolder(firstView).itemView.width
                            layoutParams.height = originalFirstItemHeight
                            tempWidth = originalFirstItemWeight
                            firstView.layoutParams = layoutParams
                        } else {
                            layoutParams.height = (Math.max((layoutParams.height + e.y - downY).toInt(), originalFirstItemHeight))
                            tempWidth = (Math.max((tempWidth + (e.y - downY) * originalFirstItemWeight / originalFirstItemHeight).toInt(), originalFirstItemWeight))
                            downY = e.y.toInt()
                            firstView.layoutParams = layoutParams

                            val viewpager = firstView.getChildAt(0) as ViewPager
                            val viewpagerLayoutParams = viewpager.layoutParams



                            viewpagerLayoutParams.height = layoutParams.height
                            viewpagerLayoutParams.width = tempWidth
                            viewpager.layoutParams = viewpagerLayoutParams

                            dx = viewpagerLayoutParams.width - originalFirstItemWeight

                            adjustViewPager(viewpager, dx)
                        }
                        return true

                    }
                }

            }
            MotionEvent.ACTION_UP -> {
                canRefresh = false
                isFirstMove = true
                if (getChildAt(0) is HomeBanner) {
                    val firstView = getChildAt(0) as HomeBanner

                    hideLoading(firstView)

                    val layoutParams = firstView.layoutParams
                    layoutParams.height = originalFirstItemHeight
                    tempWidth = originalFirstItemWeight
                    firstView.layoutParams = layoutParams

                    val viewpager = firstView.getChildAt(0) as ViewPager
                    val viewpagerLayoutParams = viewpager.layoutParams
                    viewpagerLayoutParams.height = layoutParams.height
                    viewpagerLayoutParams.width = tempWidth
                    viewpager.layoutParams = viewpagerLayoutParams

                    dx = viewpagerLayoutParams.width - originalFirstItemWeight

                    adjustViewPager(viewpager, dx)
                }


            }
        }
        return super.onTouchEvent(e)
    }

    fun adjustViewPager(viewpager: ViewPager, dx: Int) {
        viewpager.translationX = -(dx * 1f / 2)
    }


    val loading by lazy {
        val imageView = ImageView(context)
        imageView.setImageResource(R.mipmap.eye_loading_progress)
        imageView
    }

    val loadingView by lazy {
        val frameLayout = RelativeLayout(context)
        frameLayout.setBackgroundColor(0xaa000000.toInt())
        frameLayout.gravity = Gravity.CENTER
        frameLayout.addView(loading)
        frameLayout.layoutParams = ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT)
        frameLayout
    }


    var hasShow: Boolean = false
    fun showLoading(viewGroup: ViewGroup) {
        hasShow = true
        viewGroup.addView(loadingView)
    }

    fun hideLoading(viewGroup: ViewGroup) {
        hasShow = false
        viewGroup.removeView(loadingView)

    }


}