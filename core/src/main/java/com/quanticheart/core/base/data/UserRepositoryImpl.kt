package com.quanticheart.core.base.data

import com.quanticheart.core.base.data.user.UserRemoteDataSource
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.models.user.NewUser
import com.quanticheart.core.base.domain.models.user.User
import com.quanticheart.core.base.domain.models.user.UserLogin
import com.quanticheart.core.base.domain.repositories.UserRepository

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource) : UserRepository {

    override suspend fun getUserLogged(): RequestState<User> {
        return userRemoteDataSource.getUserLogged()
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return userRemoteDataSource.doLogin(userLogin)
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        return userRemoteDataSource.create(newUser)
    }

}