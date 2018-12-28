package com.xk.eyepetizer.ui.base

import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.xk.eyepetizer.playbackop.OPStates
import com.xk.eyepetizer.playbackop.State
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * Created by xuekai on 2017/8/23.
 */
abstract class BaseActivity : AppCompatActivity(), RxNetManager {
    protected val disposables = CompositeDisposable()
    internal var handler = Handler()

    /**
     * 存放当前activity中的事件
     */
    private var activityEvents: Queue<State>? = null
    /**
     * 当前activity可见之后的时间点（每次onresume之后都创建一个新的队列，同时也赋值新的statetime）
     */
    private var startTime: Long = 0

    override fun onResume() {
        super.onResume()
        record()
        play()
    }

    fun record() {
        if (OPStates.isRecord) {
            OPStates.play = false
            activityEvents = LinkedList()
            startTime = System.currentTimeMillis()
            OPStates.states.add(activityEvents)
        }
    }

    fun play() {
        if (OPStates.play) {
            OPStates.isRecord = false
            handler.postDelayed({
                Thread(Runnable {
                    if (!OPStates.states.isEmpty()) {
                        val pop = OPStates.states.remove()
                        while (!pop.isEmpty()) {
                            val state = pop.remove()
                            handler.postDelayed({
                                if (state.event == null) {
                                    //简单粗暴的认为点击返回键就是finish
                                    finish()
                                } else {
                                    dispatchTouchEvent(state.event)
                                }
                            }, state.time)
                        }
                    }
                }).start()
            }, 500)

        }
    }

    override fun onBackPressed() {
        //简单粗暴的把这里假设成点了返回键
        val state = State()
        state.event = null
        state.time = System.currentTimeMillis() - startTime
        activityEvents?.add(state)
        super.onBackPressed()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (OPStates.isRecord && activityEvents != null) {
            val obtain = MotionEvent.obtain(ev)
            val state = State()
            state.event = obtain
            state.time = System.currentTimeMillis() - startTime
            activityEvents?.add(state)
        }
        return super.dispatchTouchEvent(ev)
    }
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    override fun dispose(disposable: Disposable) {
        disposables.remove(disposable)
    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}