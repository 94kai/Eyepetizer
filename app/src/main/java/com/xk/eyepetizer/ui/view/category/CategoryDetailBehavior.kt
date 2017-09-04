package com.xk.eyepetizer.ui.view.category

import android.animation.ValueAnimator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * 自定义一个behavior，实现分类详情页的headerimage效果
 * Created by xuekai on 2017/9/3.
 */
class CategoryDetailBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<RecyclerView>(context, attrs) {

    //header
    var header: CategoryHeaderView? = null
    //recyclerview
    var recyclerview: RecyclerView? = null
    var parent: CoordinatorLayout? = null
    //header根据recyclerview的改变而改变
    var parentHeight = 0

    //确定child和dependency（behavior作用于recyclerview，所以他叫child）
    override fun layoutDependsOn(parent: CoordinatorLayout?, child: RecyclerView?, dependency: View?): Boolean {
        if (header == null) {
            header = dependency as CategoryHeaderView?
        }
        if (recyclerview == null) {
            recyclerview = child
        }
        if (parent == null) {
            this.parent = parent
        }
        if (parentHeight == 0) {
            parentHeight = parent!!.layoutParams.height
        }
        return true
    }


    var isFirstLayoutChild = true
    //此时dependency布局完成，在这里布局child
    override fun onLayoutChild(parent: CoordinatorLayout?, child: RecyclerView?, layoutDirection: Int): Boolean {
        if (isFirstLayoutChild) {
            setRecyclerViewState(header!!.height)
            isFirstLayoutChild = false
        }
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: RecyclerView?, dependency: View?): Boolean {
        Log.i("CategoryDetailBehavior", "onDependentViewChanged-->${1}")
        return super.onDependentViewChanged(parent, child, dependency)
    }


    //竖直方向全部接收
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: RecyclerView?, directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScrollAccepted(coordinatorLayout: CoordinatorLayout?, child: RecyclerView?, directTargetChild: View?, target: View?, nestedScrollAxes: Int) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: RecyclerView?, target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        if (!hasCollaspsed()) {//只要recyclerview的位置大于header最小的值，就不能上下滑动
            val marginTop = (recyclerview!!.translationY - dy).toInt()//recyclerview应该距离顶部的高度
            if ((marginTop <= header!!.getMaxHeight())&&marginTop>=header!!.getMinHeight()) {
                setRecyclerViewState(marginTop)
            }else if(marginTop<header!!.getMinHeight()){
                setRecyclerViewState(header!!.getMinHeight())
            }else if(marginTop > header!!.getMaxHeight()){
                setRecyclerViewState(header!!.getMaxHeight())
            }
            consumed!![1] = dy
        } else {//收回去了
            //如果recyclerview不能下滑了。。。
            if (!canSlideBottom()) {//不能下滑了
                if (dy < 0) {
                    val marginTop = (recyclerview!!.translationY - dy).toInt()//recyclerview应该距离顶部的高度
                    setRecyclerViewState(marginTop)
                    consumed!![1] = dy
                }

            }
        }

    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout?, child: RecyclerView?, target: View?) {
        playAnimator()
    }
    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: RecyclerView?, target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }

    override fun onNestedFling(coordinatorLayout: CoordinatorLayout?, child: RecyclerView?, target: View?, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return true
    }

    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout?, child: RecyclerView?, target: View?, velocityX: Float, velocityY: Float): Boolean {
        if (!hasCollaspsed()) {//header没有缩回去
            return true
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    /**
     * 是否可以手指上滑
     *
     * @return
     */
    private fun canSlideTop(): Boolean {
        return recyclerview!!.canScrollVertically(1)
    }

    /**
     * 是否可以手指下滑
     *
     * @return
     */
    private fun canSlideBottom(): Boolean {
        return recyclerview!!.canScrollVertically(-1)
    }

    /**
     * 设置recyclerview距离上面的距离以及高度
     */

    private fun setRecyclerViewState(height: Int) {
//        height从header.max->header.min   求出0-1

        val temp = header!!.getMinHeight() - header!!.getMaxHeight()
        val progress = height * 1f / temp - header!!.getMaxHeight() * 1f / temp
        Log.i("CategoryDetailBehavior", "setRecyclerViewState-->${progress}")
        (1f / (header!!.getMinHeight() - header!!.getMaxHeight()) * height - (1f / (header!!.getMinHeight() - header!!.getMaxHeight())))
        header!!.setCollaspsedProgress(progress)
        recyclerview!!.setTranslationY(height.toFloat())


    }

    /**
     * 是否收回去了
     */
    private fun hasCollaspsed(): Boolean {
        val translationY = recyclerview?.getTranslationY()
        if ((translationY!! > header!!.getMinHeight())) {
            return false
        }
        return true
    }

    /**
     * 展开、合上进行了一半，松手后播放动画
     */
    private fun playAnimator() {
        val translationY = recyclerview!!.translationY

        val middle = (header!!.getMinHeight() + header!!.getMaxHeight()) / 2f
        var end: Int

        if ((translationY > middle)) {
            end = header!!.getMaxHeight()
        } else {
            end = header!!.getMinHeight()
        }
        val valueAnimator = ValueAnimator.ofInt(translationY.toInt(), end)
        valueAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            setRecyclerViewState(animatedValue)
        }
        valueAnimator.duration = 100
        valueAnimator.start()
    }
}