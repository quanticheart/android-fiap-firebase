package com.quanticheart.core.base.data.remoteFeatures

import com.google.gson.Gson
import com.quanticheart.core.base.domain.models.DashboardMenu
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.extensions.fromRemoteConfig

class AppRemoteFirebaseDataSourceImpl : AppRemoteDataSource {
    override suspend fun getMinVersionApp(): RequestState<Int> {
        val minVersion =
            Gson().fromRemoteConfig(RemoteConfigKeys.MIN_VERSION_APP, Int::class.java) ?: 0
        return RequestState.Success(minVersion)
    }

    override suspend fun getDashboardMenu(): RequestState<DashboardMenu> {
        val dashboardMenu =
            Gson().fromRemoteConfig(RemoteConfigKeys.MENU_DASHBOARD, DashboardMenu::class.java)

        return if (dashboardMenu == null)
            RequestState.Error(Exception("Não foi possível encontrar o menu"))
        else
            RequestState.Success(dashboardMenu)
    }
}
