package com.example.tasks.service.repository.remote

import android.util.Log
import com.example.tasks.service.constants.TaskConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {
    companion object {
        private var personKey = ""
        private var tokenKey = ""
        private lateinit var retrofit: Retrofit
        private val bseaurl = "http://devmasterteam.com/cursoandroidAPI/"

        private fun getRetrofitInstace(): Retrofit {
            val httpClient = OkHttpClient.Builder()

            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(bseaurl)
                    .client(httpClient.build())
                    .client(getHttpClientInterceptor())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            httpClient.addInterceptor(Interceptor { chain ->
                val request =
                    chain.request()
                        .newBuilder()
                        .addHeader(TaskConstants.HEADER.PERSON_KEY, personKey)
                        .addHeader(TaskConstants.HEADER.TOKEN_KEY, tokenKey)
                        .build()
                chain.proceed(request)
            })
            return retrofit
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstace().create(serviceClass)

        }

        fun addHeaders(tokenKey: String, personKey: String) {
            this.tokenKey = tokenKey
            this.personKey = personKey
            Log.e("header", "$personKey / $tokenKey")
        }

        fun getHttpClientInterceptor(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

    }
}
