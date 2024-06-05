package com.example.bizlink.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.ui.entities.JWTLogin
import com.example.bizlink.ui.entities.User
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val _loginData = MutableLiveData<JWTLogin>()
    val loginData: LiveData<JWTLogin>
        get() = _loginData

    val errorsChannel = Channel<Throwable>()

    fun login(email:String, password:String):Boolean{
        var flag = false
        viewModelScope.launch {
            runCatching {
                ServiceLocator.loginUsecase.invoke(email, password)
            }.onSuccess {
                _loginData.value = it
                flag = true
            }.onFailure {
                errorsChannel.send(it)
            }
        }
        return flag
    }
    fun registration(user: User, password: String, repPassword:String):Boolean{
        var flag = false
        viewModelScope.launch {
            runCatching {
                ServiceLocator.registrationUsecase.invoke(user, password, repPassword)
            }.onSuccess {
                //добавляем в бд
                flag = true
            }.onFailure {
                errorsChannel.send(it)
            }
        }
        return flag
    }
}