package com.quanticheart.firebase.data

import com.quanticheart.firebase.data.remote.datasource.cars.CarRemoteDataSource
import com.quanticheart.firebase.data.remote.datasource.cars.CarRemoteFirebaseDataSourceImpl
import com.quanticheart.firebase.data.repository.CarRepositoryImpl
import com.quanticheart.firebase.domain.repository.CarRepository
import org.koin.dsl.module

//
// Created by Jonn Alves on 17/09/22.
//
val dataModule = module {
    factory<CarRemoteDataSource> { CarRemoteFirebaseDataSourceImpl(get()) }
    factory<CarRepository> { CarRepositoryImpl(get()) }
}