package com.quanticheart.core.base.domain.useCases

import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.models.user.User
import com.quanticheart.core.base.domain.models.user.UserLogin
import com.quanticheart.core.base.domain.repositories.UserRepository
import com.quanticheart.firebase.domain.exceptions.EmailBlankException
import com.quanticheart.firebase.domain.exceptions.PasswordBlankException

class LoginUseCase(private val repository: UserRepository) {
    suspend fun doLogin(userLogin: UserLogin): RequestState<User> {

        if (userLogin.email.isBlank()) {
            RequestState.Error(EmailBlankException())
        }

        if (userLogin.password.isBlank()) {
            RequestState.Error(PasswordBlankException())
        }

        return repository.doLogin(userLogin)
    }
}