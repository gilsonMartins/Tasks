package com.example.tasks.service.repository

import android.content.Context
import android.util.Log
import com.example.tasks.R
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APiListerner
import com.example.tasks.service.model.TaskModel
import com.example.tasks.service.repository.remote.RetrofitClient
import com.example.tasks.service.repository.remote.TasksService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(val context: Context) {
    private val mRemote = RetrofitClient.createService(TasksService::class.java)

    fun create(task: TaskModel, listener: APiListerner<Boolean>) {


        val call: Call<Boolean> = mRemote.create(
            task.priorityId,
            task.description,
            task.dueDate,
            task.complete
        )
        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                val code = response.code()
                if (!response.isSuccessful) {
                    val validation =
                        Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }
        })
    }
}