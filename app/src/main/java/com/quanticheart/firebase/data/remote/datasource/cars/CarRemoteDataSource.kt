package com.quanticheart.firebase.data.remote.datasource.cars

import com.quanticheart.firebase.domain.entity.Car
import com.quanticheart.core.base.domain.models.RequestState

interface CarRemoteDataSource {

    suspend fun save(car: Car): RequestState<Car>

    suspend fun findBy(id: String): RequestState<Car>
}
