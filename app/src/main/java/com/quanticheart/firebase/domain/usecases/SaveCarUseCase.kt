package com.quanticheart.firebase.domain.usecases

import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.useCases.GetUserLoggedUseCase
import com.quanticheart.firebase.domain.entity.Car
import com.quanticheart.firebase.domain.repository.CarRepository

class SaveCarUseCase(
    private val getUserLoggedUseCase: GetUserLoggedUseCase,
    private val carRepository: CarRepository
) {
    suspend fun save(car: Car): RequestState<Car> {
        return when (val userLogged = getUserLoggedUseCase.getUserLogged()) {
            is RequestState.Success -> {
                car.userId = userLogged.data.id
                carRepository.save(car)
            }

            is RequestState.Loading -> {
                RequestState.Loading
            }

            is RequestState.Error -> {
                RequestState.Error(Exception("Usuário não encontrado para associar o carro"))
            }
        }
    }

}
