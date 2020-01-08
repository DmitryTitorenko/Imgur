package com.example.imgur.view.activity

import com.arellomobile.mvp.InjectViewState
import com.example.imgur.view.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
) : BasePresenter<IMainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
