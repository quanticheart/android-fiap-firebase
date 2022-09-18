package com.quanticheart.firebase.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.models.user.User
import com.quanticheart.core.base.domain.useCases.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class BaseAuthViewModel(private val getUserLoggedUseCase: GetUserLoggedUseCase) : ViewModel() {

    val userLoggedState = MutableLiveData<RequestState<User>>()

    fun getUserLogged() {
        viewModelScope.launch {
            userLoggedState.value = getUserLoggedUseCase.getUserLogged()
        }
    }
}