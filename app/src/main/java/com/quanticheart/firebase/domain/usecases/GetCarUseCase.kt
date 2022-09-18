package com.quanticheart.firebase.domain.usecases

import com.quanticheart.firebase.domain.entity.Car
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.firebase.domain.repository.CarRepository

class GetCarUseCase(
    private val carRepository: CarRepository
) {
    suspend fun findBy(id: String): RequestState<Car> {
        return carRepository.findBy(id)
    }
}
