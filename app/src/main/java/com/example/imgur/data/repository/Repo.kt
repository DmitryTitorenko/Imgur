package com.example.imgur.data.repository

import com.example.imgur.data.network.API
import com.example.imgur.model.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
    private val api: API
) {
    suspend fun uploadImage(base64: String): Image =
        withContext(Dispatchers.IO) {
            return@withContext api.uploadImage(base64)
        }
}
