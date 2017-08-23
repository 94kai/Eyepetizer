package com.xk.eyepetizer

import android.app.Application
import com.xk.eyepetizer.util.DisplayManager

/**
 * Created by xuekai on 2017/8/21.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        DisplayManager.init(this)
    }
}