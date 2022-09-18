package com.quanticheart.core.base.domain.useCases

import com.quanticheart.core.base.domain.repositories.UserRepository

class GetUserLoggedUseCase(private val repository: UserRepository) {
    suspend fun getUserLogged() = repository.getUserLogged()
}