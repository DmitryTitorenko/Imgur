package com.example.imgur.data.network

import com.example.imgur.model.Image
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface API {
    companion object {
        const val BASE_URL = "https://api.imgur.com/"
    }

    @FormUrlEncoded
    @POST("3/image")
    suspend fun uploadImage(@Field("image") base64: String): Image
}