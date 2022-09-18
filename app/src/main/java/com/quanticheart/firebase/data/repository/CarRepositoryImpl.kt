package com.quanticheart.firebase.data.repository

import com.quanticheart.firebase.data.remote.datasource.cars.CarRemoteDataSource
import com.quanticheart.firebase.domain.entity.Car
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.firebase.domain.repository.CarRepository

class CarRepositoryImpl(
    private val carRemoteDataSource: CarRemoteDataSource
) : CarRepository {

    override suspend fun save(car: Car): RequestState<Car> {
        return carRemoteDataSource.save(car)
    }

    override suspend fun findBy(id: String): RequestState<Car> {
        return carRemoteDataSource.findBy(id)
    }

}
