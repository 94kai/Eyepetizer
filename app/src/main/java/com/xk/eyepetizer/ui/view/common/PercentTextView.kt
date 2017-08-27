package com.xk.eyepetizer.ui.view.common

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.WindowManager
import com.xk.eyepetizer.R


/**
 * 尺寸是ui图上的高度
 * Created by xuekai on 2017/4/1.
 */

class PercentTextView : android.support.v7.widget.AppCompatTextView {
    private var mTextSizePercent = 1f

    constructor(context: Context) : super(context) {
        setDefaultPercent(context)
        textSize = textSize
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getAttrs(context, attrs)
        setDefaultPercent(context)
        textSize = textSize
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getAttrs(context, attrs)
        setDefaultPercent(context)
        textSize = textSize
    }

    override fun setTextSize(unit: Int, size: Float) {
        var varSize = size
        varSize = (varSize * mTextSizePercent).toInt().toFloat()
        super.setTextSize(unit, varSize)
    }

    override fun setPaintFlags(flags: Int) {
        super.setPaintFlags(flags)
    }

    override fun setTextSize(size: Float) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    var textSizePercent: Float
        get() = mTextSizePercent
        set(textSizePercent) {
            mTextSizePercent = textSizePercent
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }

    fun setTextSizePercent(unit: Int, textSizePercent: Float) {
        mTextSizePercent = textSizePercent
        setTextSize(unit, textSize)
    }

    /**
     * 得到自定义的属性值
     *
     * @param context
     * @param attrs
     */
    private fun getAttrs(context: Context, attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.PercentTextView)
        baseScreenHeight = ta.getInt(R.styleable.PercentTextView_baseScreenHeight, baseScreenHeight)
        ta.recycle()
    }

    /**
     * 设置默认的百分比
     *
     * @param context
     */
    private fun setDefaultPercent(context: Context) {
        val screenHeight = getScreenHeight(context).toFloat()
        mTextSizePercent = screenHeight / baseScreenHeight
    }

    companion object {

        private var baseScreenHeight = 1920


        /**
         * 获取当前设备屏幕的宽高
         *
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val wm = context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return wm.defaultDisplay.height
        }
    }


}
