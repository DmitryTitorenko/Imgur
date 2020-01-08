package com.example.imgur.injection
import com.example.imgur.view.activity.MainActivity
import com.example.imgur.view.images.ImagesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindingModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindImagesFragment(): ImagesFragment
}
