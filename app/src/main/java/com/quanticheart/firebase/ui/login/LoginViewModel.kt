package com.quanticheart.firebase.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.models.user.User
import com.quanticheart.core.base.domain.models.user.UserLogin
import com.quanticheart.core.base.domain.useCases.LoginUseCase
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    val loginState = MutableLiveData<RequestState<User>>()

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            loginState.value = loginUseCase.doLogin(UserLogin(email, password))
        }
    }
}
