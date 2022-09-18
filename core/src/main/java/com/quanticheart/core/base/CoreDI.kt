package com.quanticheart.core.base

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.quanticheart.core.base.data.AppRepositoryImpl
import com.quanticheart.core.base.data.UserRepositoryImpl
import com.quanticheart.core.base.data.remoteFeatures.AppRemoteDataSource
import com.quanticheart.core.base.data.remoteFeatures.AppRemoteFirebaseDataSourceImpl
import com.quanticheart.core.base.data.user.UserRemoteDataSource
import com.quanticheart.core.base.data.user.UserRemoteFirebaseDataSourceImpl
import com.quanticheart.core.base.domain.repositories.AppRepository
import com.quanticheart.core.base.domain.repositories.UserRepository
import com.quanticheart.core.base.domain.useCases.*
import com.quanticheart.core.base.fragment.VersionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//
// Created by Jonn Alves on 17/09/22.
//
val coreModule = module {
    factory { Firebase.auth }
    factory { Firebase.firestore }
    factory<AppRemoteDataSource> { AppRemoteFirebaseDataSourceImpl() }
    factory<UserRemoteDataSource> { UserRemoteFirebaseDataSourceImpl(get(), get()) }

    factory<AppRepository> { AppRepositoryImpl(get()) }
    factory<UserRepository> { UserRepositoryImpl(get()) }

    factory { GetDashboardMenuUseCase(get()) }
    factory { GetUserLoggedUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { CreateUserUseCase(get()) }
    factory { GetMinAppVersionUseCase(get()) }

    viewModel { VersionViewModel(get()) }
}