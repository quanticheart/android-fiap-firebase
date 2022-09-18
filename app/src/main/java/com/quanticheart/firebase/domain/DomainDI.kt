package com.quanticheart.firebase.domain

import com.quanticheart.firebase.domain.usecases.CalculateBetterFuelUseCase
import com.quanticheart.firebase.domain.usecases.GetCarUseCase
import com.quanticheart.firebase.domain.usecases.SaveCarUseCase
import com.quanticheart.firebase.domain.utils.FuelCalculator
import org.koin.dsl.module

//
// Created by Jonn Alves on 17/09/22.
//
val domainModule = module {
    factory { FuelCalculator() }
    factory { CalculateBetterFuelUseCase(get()) }
    factory { GetCarUseCase(get()) }
    factory { SaveCarUseCase(get(), get()) }
}