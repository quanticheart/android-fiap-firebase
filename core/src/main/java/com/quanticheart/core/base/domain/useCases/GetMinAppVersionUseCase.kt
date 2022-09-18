package com.quanticheart.core.base.domain.useCases

import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.repositories.AppRepository

class GetMinAppVersionUseCase(private val repository: AppRepository) {
    suspend fun getMinVersionApp(): RequestState<Int> =
        repository.getMinVersionApp()
}
