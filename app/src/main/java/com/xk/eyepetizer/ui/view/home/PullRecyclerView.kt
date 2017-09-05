package com.xk.eyepetizer.ui.view.home

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.v1.xklibrary.LogUtil
import com.xk.eyepetizer.R
import com.xk.eyepetizer.ui.view.home.banner.HomeBanner

/**
 * Created by xuekai on 2017/8/23.
 */
class PullRecyclerView : RecyclerView {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        overScrollMode = OVER_SCROLL_NEVER
    }


    val pullDistance = 300//下拉高度达到这个的时候，松开手才会刷新
    var originalFirstItemHeight = 0
    var originalFirstItemWeight = 0
    var downY = -1
    //down之后下次up之前，这个值不变，用来实现loading的缩放比例
    var constDownY = -1
    var constUpY = -1f
    var canRefresh = false
    var isFirstMove = true
    var tempWidth = -1
    var dx = 0
    var homeBanner: HomeBanner? = null

    var willRefresh = false//松手后可刷新

    var mLastMotionY = 0f
    var mLastMotionX = 0f
    var deltaY = 0f
    var deleaX = 0f
    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        var resume = super.onInterceptTouchEvent(e)
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                // 发生down事件时,记录y坐标
                mLastMotionY = e.y
                mLastMotionX = e.x
                downY = e.y.toInt()
                constDownY = e.y.toInt()
                resume = false;
            }
            MotionEvent.ACTION_MOVE -> {
                // deltaY > 0 是向下运动,< 0是向上运动
                deltaY = e.y.minus(mLastMotionY)
                deleaX = e.x.minus(mLastMotionX)

                if (Math.abs(deleaX) > Math.abs(deltaY)) {
                    resume = false;
                } else {
                    //当前正处于滑动
                    if (!canScrollVertically(-1) && !willRefresh) {
                        canRefresh = true
                    }
                    resume = true
                }
            }
            MotionEvent.ACTION_UP -> {
                resume = false
            }

        }
        return resume;
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {

        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                downY = e.y.toInt()
                constDownY = e.y.toInt()
                if (!canScrollVertically(-1) && !willRefresh) {
                    canRefresh = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isFirstMove) {
                    isFirstMove = false
                    if (canRefresh) {
                        canRefresh = e.y - downY > 0
                    }

                }


                if (canRefresh) {
                    if (getChildAt(0) is HomeBanner) {

                        val firstView = getChildAt(0) as HomeBanner
                        if (!hasShow) {
                            showLoading(firstView)
                        }


                        var fl = e.y - constDownY//fl从1-pullDistance   缩放比例从0-1
                        if ((fl <= 0)) {
                            return true
                        }
                        setLoadingScale(fl)


                        val layoutParams = firstView.layoutParams
                        if (layoutParams.height < 0 || tempWidth < 0) {
                            originalFirstItemHeight = getChildViewHolder(firstView).itemView.height
                            originalFirstItemWeight = getChildViewHolder(firstView).itemView.width
                            layoutParams.height = originalFirstItemHeight
                            tempWidth = originalFirstItemWeight
                            firstView.layoutParams = layoutParams
                        } else {

                            var dY = e.y - downY
                            val fl1 = e.y - constDownY


                            val ratio = (1f / (0.004 * fl1 + 1)).toFloat()//实现阻尼效果
                            dY = dY * ratio
                            layoutParams.height = (Math.max((layoutParams.height + dY).toInt(), originalFirstItemHeight))
                            tempWidth = (Math.max((tempWidth + dY * originalFirstItemWeight / originalFirstItemHeight).toInt(), originalFirstItemWeight))
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
                constUpY = e.y
                if (getChildAt(0) is HomeBanner) {
                    getChildAt(0)?.let {
                        smoothRecover()
                    }
                }
            }
        }
        return super.onTouchEvent(e)
    }


    val loadAnimation by lazy {
        val rotateAnimation = RotateAnimation(0f, 365f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 500
        rotateAnimation.repeatCount = -1
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation
    }

    /**
     * 松手后恢复
     */
    private fun smoothRecover() {

        if (originalFirstItemHeight != 0) {
            homeBanner = getChildAt(0) as HomeBanner
            val layoutParams = homeBanner?.layoutParams
            homeBanner?.layoutParams = layoutParams


            val viewpager = homeBanner?.getChildAt(0) as ViewPager
            val viewpagerLayoutParams = viewpager.layoutParams
            if (loading.scaleX == 1f) {
                willRefresh = true
            }
            var dYForView = layoutParams!!.height - originalFirstItemHeight

//            layoutParams!!.height, originalFirstItemHeight
            val homeBannerAnimator = ValueAnimator.ofInt(layoutParams.height, originalFirstItemHeight)
            homeBannerAnimator
                    .addUpdateListener { animation ->
                        layoutParams.height = animation.animatedValue as Int
                        tempWidth = (animation.animatedValue as Int * (originalFirstItemWeight * 1f / originalFirstItemHeight)).toInt()
                        homeBanner?.layoutParams = layoutParams


                        viewpagerLayoutParams.height = layoutParams.height
                        dx = viewpagerLayoutParams.width - originalFirstItemWeight

                        viewpagerLayoutParams.width = tempWidth
                        viewpager.layoutParams = viewpagerLayoutParams
                        LogUtil.d("${viewpagerLayoutParams.width}-->${viewpagerLayoutParams.height}-->");

                        dx = viewpagerLayoutParams.width - originalFirstItemWeight

                        adjustViewPager(viewpager, dx)

                        if (!willRefresh) {
                            var distanceY: Float = (layoutParams.height - originalFirstItemHeight) * 1f//算出来的是从view增加的高度到0的值，需要把它映射到手指滑动的高度到0
                            var fl = distanceY * ((constUpY - constDownY) / dYForView)//映射
                            setLoadingScale(fl)

                        }

                    }
            homeBannerAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (willRefresh) {
                        onRefreshListner?.onRefresh()
                        loading.startAnimation(loadAnimation)
                    } else {
                        hideLoading()
                    }
                }
            })
            homeBannerAnimator.setDuration(100)
            homeBannerAnimator.start()
        }


    }

    /**
     * 根据距离原始位置的高度，计算loasing的缩放值
     */
    private fun setLoadingScale(distanceY: Float) {
        var distance = (distanceY - 150) / pullDistance//下拉超过150之后开始逐渐出现loading
        if (distance >= 1) {
            distance = 1f
        } else if (distance < 0) {
            distance = 0f
        }
        loading.scaleX = distance
        loading.scaleY = distance
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

    fun hideLoading() {
        hasShow = false
        willRefresh = false
        loadAnimation.cancel()
        homeBanner?.let({
            it.removeView(loadingView)
        })

    }

    interface OnRefreshListener {
        fun onRefresh()
    }

    var onRefreshListner: OnRefreshListener? = null

    fun setOnRefreshListener(listener: OnRefreshListener) {
        this.onRefreshListner = listener
    }

}