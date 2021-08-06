package com.example.tasks.service.listener

import com.example.tasks.service.model.HeaderModel

interface APiListerner<T> {
    fun onSuccess(model: T)
    fun onFailure(model: String)
}