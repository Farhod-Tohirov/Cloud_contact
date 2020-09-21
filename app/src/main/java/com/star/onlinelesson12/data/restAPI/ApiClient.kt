package com.star.onlinelesson12.data.restAPI

import com.readystatesoftware.chuck.ChuckInterceptor
import com.star.onlinelesson11.data.LocalStorage
import com.star.onlinelesson12.app.App
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://161.35.73.101:99/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @JvmStatic
    fun <T> createService(service: Class<T>): T {
        val newRetrofit = retrofit.newBuilder().client(okHttpClientWithChuck).build()
        return newRetrofit.create(service)
    }

    fun <T> createServiceWithAuth(service: Class<T>): T {
        val newRetrofit = retrofit.newBuilder().client(okHttpClient).build()
        return newRetrofit.create(service)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ChuckInterceptor(App.instance))
        .addInterceptor {
            val requestOld = it.request()
            val token = LocalStorage.instance.token
            val request = requestOld.newBuilder()
                .removeHeader("token")
                .addHeader("token", token)
                .build()
            val response = it.proceed(request)
            response
        }
        .build()

    private val okHttpClientWithChuck = OkHttpClient.Builder()
        .addInterceptor(ChuckInterceptor(App.instance))
        .build()

}