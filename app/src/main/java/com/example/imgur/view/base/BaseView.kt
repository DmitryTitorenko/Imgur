package com.example.imgur.view.base

import com.arellomobile.mvp.MvpView

interface BaseView : MvpView {
    fun showMessage(message: String)
}
