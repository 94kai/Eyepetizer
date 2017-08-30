package com.example.v1.xklibrary;

import android.util.Log
import com.xk.eyepetizer.TAG
import kotlin.experimental.and

class LogUtil {
    val TAG: String = "Eyepetizer";

    private val LV: Byte = 0x20;
    private val LI: Byte = 0x08;
    private val LW: Byte = 0x04;
    private val LE: Byte = 0x02;
    private val LA: Byte = 0x01;



    companion object {
        private val LD: Byte = 0x10;
        val isDisplayLog: Boolean = true;
        val sLevel: Byte = 0x3f;

        fun d(msg: String) {
            if ((LD and sLevel) > 0 && isDisplayLog) {
                Log.i(TAG, msg);
            }
        }
    }
}
