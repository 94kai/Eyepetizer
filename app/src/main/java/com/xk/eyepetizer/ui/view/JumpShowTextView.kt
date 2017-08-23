package com.xk.eyepetizer.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.xk.eyepetizer.io_main
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Created by xuekai on 2017/8/22.
 */
class JumpShowTextView : FrameLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }


    var paint: Paint? = null
    var content: String? = ""

    var isBold: Boolean = false
    var color: Int = Color.BLACK

    var textSize: Float = 52F

    //线程正在运行
    var isRun: Boolean = false

    var marginBottom: Float = 0f

    var text: String? = ""
        set(value) {
            if (isRun) {

            }
            field = value
            computeViewSize()
            start()
        }


    private fun computeViewSize() {
        var paint = Paint()
        paint.setTextSize(textSize);
        var rect = Rect()   // 使用上面的画笔最终绘制出字符串所占的矩形
        text?.let {
            paint.getTextBounds(it, 0, it.length, rect); // 四个参数分别为字符串，起始位置，结束位置，矩形
        }
        val textWidth = rect.width()
        val textHeight = rect.height()
        val layoutParams = LinearLayout.LayoutParams(textWidth+5, textHeight)//测量出来的稍微有一丢丢不准确，所以加5（大概是因为float转int缺失了？）
        layoutParams.bottomMargin = marginBottom.toInt()
        this.layoutParams = layoutParams

        invalidate()
    }


    private fun init() {
        paint = Paint()
        setBackgroundColor(0x00000000)
    }

    var subscribe: Disposable? = null

    fun start() {
        if (isRun) {
            subscribe?.dispose()
        }
        content = ""

        paint?.isFakeBoldText = isBold
        paint?.color = color
        paint?.setTextSize(textSize);

        text?.let {
            isRun = true
            subscribe = Observable.interval(50, TimeUnit.MILLISECONDS)
                    .take(it.length.toLong())
                    .io_main()
                    .subscribe({ i ->
                        content = content + it[i.toInt()]
                        invalidate()
                    }, { e -> e.printStackTrace() }, { isRun = false }
                    )
        }


    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val fontMetrics = paint?.getFontMetrics()

        val centerPoint = measuredHeight / 2 - (fontMetrics!!.top + fontMetrics?.bottom) / 2
//drawText的第二个参数的值=要让文字的中心放在哪-（fontMetrics.top+fontMetrics.bottom）/2
//此时求出来的baseline可以使文字竖直居中

        canvas?.drawText(content, 0f, centerPoint, paint)


    }
}