package com.example.tasks.service.listener

import com.example.tasks.service.model.HeaderModel

interface APiListerner {
    fun onSuccess(model: HeaderModel)
    fun onFailure(model: String)
}