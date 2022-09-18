package com.quanticheart.firebase

import android.app.Application
import com.quanticheart.core.base.coreModule
import com.quanticheart.firebase.data.dataModule
import com.quanticheart.firebase.domain.domainModule
import com.quanticheart.firebase.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

//
// Created by Jonn Alves on 17/09/22.
//
class FireBaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@FireBaseApplication)
            // use modules
            modules(coreModule + dataModule + domainModule + uiModule)
        }
    }
}