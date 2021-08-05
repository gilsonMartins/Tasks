package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.HeaderModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APiListerner
import com.example.tasks.service.listener.ValidationListerner
import com.example.tasks.service.repository.PersonRepository
import com.example.tasks.service.repository.local.SecurityPreferences

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mSecurityPreferences = SecurityPreferences(application)

    private val mCreate = MutableLiveData<ValidationListerner>()
    var create: LiveData<ValidationListerner> = mCreate

    fun create(name: String, email: String, password: String) {
        mPersonRepository.create(name, email, password, object : APiListerner {
            override fun onSuccess(model: HeaderModel) {
                mSecurityPreferences.store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                mSecurityPreferences.store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                mSecurityPreferences.store(TaskConstants.SHARED.PERSON_NAME, model.name)
                mCreate.value = ValidationListerner()
            }

            override fun onFailure(model: String) {
                mCreate.value = ValidationListerner(model)
            }

        })
    }

}