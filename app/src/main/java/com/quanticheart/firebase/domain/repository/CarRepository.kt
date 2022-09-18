package com.quanticheart.firebase.domain.repository

import com.quanticheart.firebase.domain.entity.Car
import com.quanticheart.core.base.domain.models.RequestState

interface CarRepository {

    suspend fun save(car: Car): RequestState<Car>

    suspend fun findBy(id: String): RequestState<Car>
}
