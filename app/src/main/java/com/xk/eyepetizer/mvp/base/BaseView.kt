package com.xk.eyepetizer.mvp.base

interface BaseView<T> {
    fun setPresenter(presenter: T)
}