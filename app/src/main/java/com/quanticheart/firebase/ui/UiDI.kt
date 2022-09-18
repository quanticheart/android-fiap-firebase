package com.quanticheart.firebase.ui

import com.quanticheart.firebase.ui.auth.BaseAuthViewModel
import com.quanticheart.firebase.ui.betterfuel.BetterFuelViewModel
import com.quanticheart.firebase.ui.home.HomeViewModel
import com.quanticheart.firebase.ui.login.LoginViewModel
import com.quanticheart.firebase.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//
// Created by Jonn Alves on 17/09/22.
//
val uiModule = module {
    viewModel { BaseAuthViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { BetterFuelViewModel(get(), get(), get()) }
}