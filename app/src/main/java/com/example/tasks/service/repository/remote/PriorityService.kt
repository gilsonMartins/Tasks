package com.example.tasks.service.repository.remote

import com.example.tasks.service.model.PrioriryModel
import retrofit2.Call
import retrofit2.http.GET

interface PriorityService {
    @GET("Priority")
    fun list(): Call<List<PrioriryModel>>
}