package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.listener.APiListerner
import com.example.tasks.service.listener.ValidationListerner
import com.example.tasks.service.model.PriorityModel
import com.example.tasks.service.model.TaskModel
import com.example.tasks.service.repository.PriorityRepository
import com.example.tasks.service.repository.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mPrioriteRepository = PriorityRepository(application)
    private val mTaskRepository = TaskRepository(application)

    private val mPriorityList = MutableLiveData<List<PriorityModel>>()
    var priorities: LiveData<List<PriorityModel>> = mPriorityList
    private val mValidationList = MutableLiveData<ValidationListerner>()
    var validation: LiveData<ValidationListerner> = mValidationList

    fun listPriorites() {
        mPriorityList.value = mPrioriteRepository.list()
    }

    fun save(task: TaskModel) {
        mTaskRepository.create(task, object : APiListerner<Boolean> {
            override fun onSuccess(model: Boolean) {
                mValidationList.value = ValidationListerner()
            }

            override fun onFailure(model: String) {
                mValidationList.value = ValidationListerner(model)

            }

        })
    }


}