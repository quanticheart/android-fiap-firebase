package com.quanticheart.core.base.domain.useCases

import com.quanticheart.core.base.domain.models.user.NewUser
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.models.user.User
import com.quanticheart.core.base.domain.repositories.UserRepository

class CreateUserUseCase(private val userRepository: UserRepository) {
    suspend fun create(newUser: NewUser): RequestState<User> =
        userRepository.create(newUser)
}
