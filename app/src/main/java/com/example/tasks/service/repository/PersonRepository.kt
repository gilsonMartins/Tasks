package com.example.tasks.service.repository

import android.content.Context
import com.example.tasks.R
import com.example.tasks.service.model.HeaderModel
import com.example.tasks.service.listener.APiListerner
import com.example.tasks.service.repository.remote.PersonService
import com.example.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(val context: Context) {

    private val mRemote = RetrofitClient.createService(PersonService::class.java)

    fun login(email: String, password: String, listener: APiListerner) {
        val call: Call<HeaderModel> = mRemote.login(email, password)
        call.enqueue(object : Callback<HeaderModel> {
            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener.onSuccess(it)
                    }
                } else {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                }
            }


            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.onFailure(t.message.toString())

            }

        })
    }

    fun create(name: String, email: String, password: String, listener: APiListerner) {
        val call: Call<HeaderModel> = mRemote.create(name, email, password)
        call.enqueue(object : Callback<HeaderModel> {
            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener.onSuccess(it)
                    }
                } else {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                }
            }


            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.onFailure(t.message.toString())

            }

        })
    }
}