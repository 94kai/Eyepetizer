package com.xk.eyepetizer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import java.util.*


/**
 * Created by xuekai on 2017/8/20.
 */
const val TAG = "Eyepetizer"


// TODO: by xk 2017/8/19 07:07 改写单例
fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(this, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

// TODO: by xk 2017/8/19 07:07 改写单例
fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

// TODO: by xk 2017/8/19 07:07 改写单例
fun View.showToast(content: String): Toast {
    val toast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

// TODO: by xk 2017/8/19 07:28 关于inline reified
inline fun <reified T : Activity> Context.toActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Activity> Context.toActivityWithSerializable(data: Serializable) {
    val intent = Intent(this, T::class.java)
    intent.putExtra("data", data)
    startActivity(intent)
}

fun <T> Observable<T>.io_main(): Observable<T> {
    return subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}


fun View.durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    if (minute <= 9) {
        if (second <= 9) {
            return "0${minute}' 0${second}''"
        } else {
            return "0${minute}' ${second}''"
        }
    } else {
        if (second <= 9) {
            return "${minute}' 0${second}''"
        } else {
            return "${minute}' ${second}''"
        }
    }
}

fun View.timeFormat(time: Long): String {
    val date = Date(time)
    val timeCalendar = Calendar.getInstance()
    timeCalendar.time = date


    val today = Calendar.getInstance()
    val todayDate = Date(System.currentTimeMillis())
    today.time = todayDate

    if (timeCalendar.get(Calendar.YEAR) === today.get(Calendar.YEAR)) {
        val diffDay = timeCalendar.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR)

        if (diffDay == 0) {
            //是今天
            val hours = timeCalendar.get(Calendar.HOUR_OF_DAY)
            val minues = timeCalendar.get(Calendar.MINUTE)
            return "${if (hours < 10) "0" + hours else hours}:${if (minues < 10) "0" + minues else minues}"
        }
    }
    val year = timeCalendar.get(Calendar.YEAR)
    val month = timeCalendar.get(Calendar.MONTH)
    val day = timeCalendar.get(Calendar.DAY_OF_MONTH)
    return "${year}/${if (month < 10) "0" + month else month}/${if (day < 10) "0" + day else day}"
}

/**
 * 几天前  几小时前
 */
fun View.timePreFormat(time: Long): String {

    val now =System.currentTimeMillis()
    val pre = now - time//多久前


    val min = pre / 1000 / 60
    if (min<1){
        return "刚刚"
    }else if(min<60){
        return ""+min+"分钟前"
    }else if(min<60*24){
        return ""+min/60+"小时前"
    }else {
        return ""+min/60/24+"天前"
    }
}

fun Context.dataFormat(total: Long): String {
    var result = ""
    var speedReal = 0
    speedReal = (total / (1024)).toInt()
    if (speedReal < 512) {
        result = speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        result = (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}

/**
 * 1表示wifi
 */
fun Context.getNetType(): Int {
    val connectService = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectService.activeNetworkInfo


    if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
        return 0
    } else {
        // NetworkInfo不为null开始判断是网络类型
        val netType = activeNetworkInfo?.getType()
        if (netType == ConnectivityManager.TYPE_WIFI) {
            // wifi net处理
            return 1
        }
    }
    return 0
}