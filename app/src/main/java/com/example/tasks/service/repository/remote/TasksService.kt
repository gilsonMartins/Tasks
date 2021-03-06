package com.example.tasks.service.repository.remote

import com.example.tasks.service.model.TaskModel
import retrofit2.Call
import retrofit2.http.*

interface TasksService {

    @GET("Task")
    fun all(): Call<List<TaskModel>>

    @GET("Task/Next7Days")
    fun nextWeek(): Call<List<TaskModel>>

    @GET("Task/Overdue")
    fun overdue(): Call<List<TaskModel>>

    @GET("Task/{id}")
    fun load(@Path(value = "id", encoded = true) id: Int): Call<TaskModel>

    @POST("Task")
    @FormUrlEncoded
    fun create(
        @Field("PriorityId") priorityId: Int,
        @Field("Description") description: String,
        @Field("DueDate") dueDate: String,
        @Field("Complete") complete: Boolean
    ): Call<Boolean>

    @HTTP(method = "PUT", path = "Task")
    @FormUrlEncoded
    fun update(
        @Field("PriorityId") priorityId: Int,
        @Field("Description") description: String,
        @Field("DueDate") dueDate: String,
        @Field("Complete") complete: Boolean
    ): Call<Boolean>

    @HTTP(method = "PUT", path = "Task/Complete")
    @FormUrlEncoded
    fun complete(
        @Field("id") priorityId: Int
    ): Call<Boolean>

    @HTTP(method = "PUT", path = "Task/Undo")
    @FormUrlEncoded
    fun undo(
        @Field("Id") priorityId: Int
    ): Call<Boolean>

    @HTTP(method = "DELETE", path = "Task", hasBody = true)
    @FormUrlEncoded
    fun delete(
        @Field("id") priorityId: Int
    ): Call<Boolean>
}