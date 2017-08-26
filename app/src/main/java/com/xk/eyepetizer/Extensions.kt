package com.xk.eyepetizer

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable

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


fun View.timeFromat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration!! % 60
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

