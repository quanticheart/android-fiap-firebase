package com.quanticheart.firebase.data.remote.datasource.cars

import com.quanticheart.firebase.domain.entity.Car
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.firebase.domain.exceptions.CarNotFoundExcetion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CarRemoteFirebaseDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : CarRemoteDataSource {

    override suspend fun save(car: Car): RequestState<Car> {
        return try {
            firebaseFirestore.collection("cars")
                .document(car.userId)
                .set(car)
                .await()
            RequestState.Success(car)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun findBy(id: String): RequestState<Car> {
        return try {

            val car = firebaseFirestore.collection("cars")
                .document(id)
                .get()
                .await()
                .toObject(Car::class.java)

            if(car == null)
                RequestState.Error(CarNotFoundExcetion())
            else
                RequestState.Success(car)

        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

}
