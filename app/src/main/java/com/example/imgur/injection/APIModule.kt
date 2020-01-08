package com.example.imgur.injection

import com.example.imgur.data.network.API
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class APIModule {
    @Provides
    @Singleton
    fun buildRetrofitConfig(): API {
        val httpClient = getBaseHttpConfig()
        val retrofit = Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        return retrofit.build().create(API::class.java)
    }

    private fun getBaseHttpConfig(): OkHttpClient.Builder {
        val log = HttpLoggingInterceptor()
        log.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(log)
        okHttpClient.addInterceptor {
            var request = it.request()
            var builder = request.newBuilder()
            builder.addHeader("Authorization", "Client-ID 8a2dea05d9e90a8")
            request = builder.build()
            it.proceed(request)
        }
        return okHttpClient
    }
}
