package com.quanticheart.core.base.domain.repositories

import com.quanticheart.core.base.domain.models.DashboardMenu
import com.quanticheart.core.base.domain.models.RequestState

interface AppRepository {
    suspend fun getMinVersionApp(): RequestState<Int>
    suspend fun getDashboardMenu(): RequestState<DashboardMenu>
}
