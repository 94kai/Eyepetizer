package com.xk.eyepetizer.net

import com.xk.eyepetizer.ui.base.BaseActivity
import com.xk.eyepetizer.ui.base.BaseFragment
import io.reactivex.observers.DisposableObserver

/**
 * Created by xuekai on 2017/8/23.
 */
 class NetObserver<T> : DisposableObserver<T> {

    var activity: BaseActivity? = null
    var fragment: BaseFragment? = null
    var onSuccess:OnSuccess?=null

    constructor(activity: BaseActivity?,onSuccess: OnSuccess) : super() {
        this.activity = activity
        this.onSuccess = onSuccess
    }

    constructor(fragment: BaseFragment?,onSuccess: OnSuccess) : super() {
        this.fragment = fragment
        this.onSuccess = onSuccess
    }


    override fun onComplete() {

        activity?.dispose(this)
        fragment?.dispose(this)
    }

    override fun onError(e: Throwable) {
// TODO: by xk 2017/8/23 16:05 处理错误
        e.printStackTrace()

        activity?.dispose(this)
        fragment?.dispose(this)
    }

    override fun onNext(t: T) {
        onSuccess?.onSuccess()
    }

    interface OnSuccess{

        fun onSuccess()
    }
    override fun onStart() {
        super.onStart()
        activity?.addDisposable(this)
        fragment?.addDisposable(this)
    }
}