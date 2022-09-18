package com.quanticheart.firebase.ui.betterfuel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quanticheart.firebase.domain.entity.Car
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.firebase.domain.entity.enums.FuelType
import com.quanticheart.firebase.domain.entity.BetterFuelHolder
import com.quanticheart.firebase.domain.usecases.CalculateBetterFuelUseCase
import com.quanticheart.firebase.domain.usecases.GetCarUseCase
import com.quanticheart.firebase.domain.usecases.SaveCarUseCase
import kotlinx.coroutines.launch

class BetterFuelViewModel(
    private val saveCarUseCase: SaveCarUseCase,
    private val calculateBetterFuelUseCase: CalculateBetterFuelUseCase,
    private val getCarUseCase: GetCarUseCase
) : ViewModel() {

    var calculateState = MutableLiveData<RequestState<FuelType>>()
    private var saveCarState = MutableLiveData<RequestState<FuelType>>()

    val carSelectedState = MutableLiveData<RequestState<Car>>()

    fun getCar(id: String) {
        viewModelScope.launch {
            carSelectedState.value = getCarUseCase.findBy(id)
        }
    }

    fun calculateBetterFuel(
        vehicle: String,
        kmGasolinePerLiter: Double,
        kmEthanolPerLiter: Double,
        priceGasolinePerLiter: Double,
        priceEthanolPerLiter: Double
    ) {
        val car = Car(
            vehicle,
            kmGasolinePerLiter,
            kmEthanolPerLiter,
            priceGasolinePerLiter,
            priceEthanolPerLiter,
            ""
        )

        viewModelScope.launch {
            val params = CalculateBetterFuelUseCase.Params(
                BetterFuelHolder(
                    car.kmEthanolPerLiter,
                    car.kmGasolinePerLiter,
                    car.priceEthanolPerLiter,
                    car.priceGasolinePerLiter
                )
            )

            calculateState.value = calculateBetterFuelUseCase.calculate(params)

            when (saveCarUseCase.save(car)) {
                is RequestState.Success -> {
                    saveCarState.value = calculateBetterFuelUseCase.calculate(params)
                }
                is RequestState.Error -> {
                    saveCarState.value =
                        RequestState.Error(Exception("Não foi possível gravar o carro no banco"))
                }
                is RequestState.Loading -> {
                    saveCarState.value = RequestState.Loading
                }
            }
        }
    }
}