package com.example.imgur.view.base

import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<TPresenterView : BaseView> : MvpPresenter<TPresenterView>(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { coroutineContext, throwable ->
            handleError(throwable)
        }

    protected fun handleError(e: Throwable) {
        e.printStackTrace()
        viewState.showMessage(e.message.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
