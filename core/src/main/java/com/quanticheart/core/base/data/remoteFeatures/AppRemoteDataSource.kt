package com.quanticheart.core.base.data.remoteFeatures

import com.quanticheart.core.base.domain.models.DashboardMenu
import com.quanticheart.core.base.domain.models.RequestState

interface AppRemoteDataSource {

    suspend fun getMinVersionApp(): RequestState<Int>

    suspend fun getDashboardMenu(): RequestState<DashboardMenu>

}
