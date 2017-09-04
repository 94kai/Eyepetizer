package com.xk.eyepetizer.ui.base

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by xuekai on 2017/8/23.
 */
abstract class BaseActivity : AppCompatActivity(), RxNetManager {
    protected val disposables = CompositeDisposable()

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