package com.example.tasks.service.repository.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        private lateinit var retrofit: Retrofit
        private val bseaurl = "http://devmasterteam.com/CursoAndroidAPI/"
        private fun getRetrofitInstace(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (!::retrofit.isInitialized){
                retrofit = Retrofit.Builder()
                    .baseUrl(bseaurl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
        fun <S> createService(serviceClass: Class<S>): S{
            return getRetrofitInstace().create(serviceClass)

        }
    }
}
