package com.example.imgur.view.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.imgur.view.base.mvp.MvpAppCompatFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseFragment : MvpAppCompatFragment(), BaseView, HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun showMessage(message: String) {
        (activity as? BaseActivity)?.showMessage(message)
    }
}
