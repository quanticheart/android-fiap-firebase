package com.quanticheart.core.base.data

import com.quanticheart.core.base.data.remoteFeatures.AppRemoteDataSource
import com.quanticheart.core.base.domain.models.DashboardMenu
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.repositories.AppRepository

class AppRepositoryImpl(private val appRemoteDataSource: AppRemoteDataSource) : AppRepository {
    override suspend fun getMinVersionApp(): RequestState<Int> {
        return appRemoteDataSource.getMinVersionApp()
    }

    override suspend fun getDashboardMenu(): RequestState<DashboardMenu> {
        return appRemoteDataSource.getDashboardMenu()
    }
}
