package com.quanticheart.core.base.domain.useCases

import com.quanticheart.core.base.domain.models.DashboardMenu
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.repositories.AppRepository

class GetDashboardMenuUseCase(private val repository: AppRepository) {
    suspend fun getDashboardMenu(): RequestState<DashboardMenu> =
        repository.getDashboardMenu()
}
