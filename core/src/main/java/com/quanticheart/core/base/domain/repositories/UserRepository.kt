package com.quanticheart.core.base.domain.repositories

import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.models.user.NewUser
import com.quanticheart.core.base.domain.models.user.User
import com.quanticheart.core.base.domain.models.user.UserLogin

interface UserRepository {
    suspend fun getUserLogged(): RequestState<User>

    suspend fun doLogin(userLogin: UserLogin): RequestState<User>

    suspend fun create(newUser: NewUser): RequestState<User>
}
