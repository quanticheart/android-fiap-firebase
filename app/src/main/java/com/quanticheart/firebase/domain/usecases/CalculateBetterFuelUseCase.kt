package com.quanticheart.firebase.domain.usecases

import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.firebase.domain.entity.BetterFuelHolder
import com.quanticheart.firebase.domain.entity.enums.FuelType
import com.quanticheart.firebase.domain.utils.FuelCalculator

class CalculateBetterFuelUseCase(private val fuelCalculator: FuelCalculator) {
    suspend fun calculate(
        params: Params
    ): RequestState<FuelType> {

        return try {
            val betterFuel = fuelCalculator.betterFuel(params.betterFuelHolder)
            RequestState.Success(betterFuel)
        } catch (ex: Exception) {
            RequestState.Error(ex)
        }
    }

    data class Params(
        val betterFuelHolder: BetterFuelHolder
    )
}