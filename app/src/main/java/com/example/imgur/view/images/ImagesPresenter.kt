package com.example.imgur.view.images

import com.arellomobile.mvp.InjectViewState
import com.example.imgur.data.repository.Repo
import com.example.imgur.view.base.BasePresenter

import kotlinx.coroutines.launch
import javax.inject.Inject

@InjectViewState
class ImagesPresenter @Inject constructor(private val repo: Repo) :
    BasePresenter<IImagesView>() {

    fun uploadImage(base64: String) = launch {
        val response = repo.uploadImage(base64)
        viewState.handleUploadImage(response)
    }
}
