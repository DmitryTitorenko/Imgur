package com.example.imgur.view.images

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.imgur.model.Image
import com.example.imgur.view.base.BaseView

interface IImagesView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun handleUploadImage(image: Image)
}