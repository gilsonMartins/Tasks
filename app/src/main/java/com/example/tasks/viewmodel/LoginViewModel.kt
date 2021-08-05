package com.example.tasks.viewmodel

import android.app.Application
import android.renderscript.RenderScript
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.model.HeaderModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APiListerner
import com.example.tasks.service.listener.ValidationListerner
import com.example.tasks.service.repository.PersonRepository
import com.example.tasks.service.repository.PriorityRepository
import com.example.tasks.service.repository.local.SecurityPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonREpository = PersonRepository(application)
    private val mPriorityRepository = PriorityRepository(application)
    private val mSheredPreferences = SecurityPreferences(application)
    private val mLogin = MutableLiveData<ValidationListerner>()
    val login: LiveData<ValidationListerner> = mLogin
    private val mLogged = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = mLogged

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        mPersonREpository.login(email, password, object : APiListerner{
            override fun onSuccess(model: HeaderModel) {

                mSheredPreferences.store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                mSheredPreferences.store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                mSheredPreferences.store(TaskConstants.SHARED.PERSON_NAME,model.name)

                mLogin.value = ValidationListerner()
            }

            override fun onFailure(model: String) {
                mLogin.value = ValidationListerner(model)
            }
        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {

        val token = mSheredPreferences.get(TaskConstants.SHARED.TOKEN_KEY)
        val person = mSheredPreferences.get(TaskConstants.SHARED.PERSON_KEY)

        val logged = (token.isNotEmpty() && person.isNotEmpty())
        if (!logged){
            mPriorityRepository.all()
        }
        mLogged.value = logged

    }

}