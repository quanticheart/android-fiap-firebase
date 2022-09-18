package com.quanticheart.firebase.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quanticheart.core.base.domain.models.user.NewUser
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.models.user.User
import com.quanticheart.core.base.domain.useCases.CreateUserUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {
    val newUserState = MutableLiveData<RequestState<User>>()

    fun create(name: String, email: String, phone: String, password: String) {
        viewModelScope.launch {
            newUserState.value = createUserUseCase.create(
                NewUser(
                    name,
                    email,
                    phone,
                    password
                )
            )
        }
    }
}
