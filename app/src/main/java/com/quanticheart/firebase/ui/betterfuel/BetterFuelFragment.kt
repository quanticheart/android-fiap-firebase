package com.quanticheart.firebase.ui.betterfuel

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.extensions.getDouble
import com.quanticheart.core.extensions.getString
import com.quanticheart.core.watchers.DecimalTextWatcher
import com.quanticheart.firebase.R
import com.quanticheart.firebase.domain.entity.enums.FuelType
import com.quanticheart.firebase.ui.auth.BaseAuthFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BetterFuelFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_better_fuel

    private lateinit var etCar: EditText
    private lateinit var etKmGasoline: EditText
    private lateinit var etKmEthanol: EditText
    private lateinit var etPriceGasoline: EditText
    private lateinit var etPriceEthanol: EditText
    private lateinit var btCalculate: Button
    private lateinit var btClear: TextView

    private val betterFuelViewModel: BetterFuelViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        setUpListener()

        registerObserver()

        betterFuelViewModel.getCar(arguments?.getString("id") ?: "")
    }

    private fun setUpView(view: View) {
        etCar = view.findViewById(R.id.etCar)
        etKmGasoline = view.findViewById(R.id.etKmGasoline)
        etKmEthanol = view.findViewById(R.id.etKmEthanol)
        etPriceGasoline = view.findViewById(R.id.etPriceGasoline)
        etPriceEthanol = view.findViewById(R.id.etPriceEthanol)
        btCalculate = view.findViewById(R.id.btCalculate)
        btClear = view.findViewById(R.id.btClear)
    }

    private fun setUpListener() {
        etPriceGasoline.addTextChangedListener(DecimalTextWatcher(etPriceGasoline))
        etPriceEthanol.addTextChangedListener(DecimalTextWatcher(etPriceEthanol))

        etKmGasoline.addTextChangedListener(DecimalTextWatcher(etKmGasoline, 1))
        etKmEthanol.addTextChangedListener(DecimalTextWatcher(etKmEthanol, 1))

        btCalculate.setOnClickListener {
            betterFuelViewModel.calculateBetterFuel(
                etCar.getString(),
                etKmGasoline.getDouble(),
                etKmEthanol.getDouble(),
                etPriceGasoline.getDouble(),
                etPriceEthanol.getDouble()
            )
        }

        btClear.setOnClickListener { clearFields() }
    }

    private fun registerObserver() {
        betterFuelViewModel.calculateState.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()

                    val betterFuelMessage = when (it.data) {
                        FuelType.GASOLINE -> "Melhor abastecer com gasolina"
                        FuelType.ETHANOL -> "Melhor abastecer com álcool"
                    }

                    showMessage(betterFuelMessage)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> {
                    showLoading("Calculando o melhor combustível para você")
                }
            }
        }


        betterFuelViewModel.carSelectedState.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Success -> {
                    val car = it.data
                    etCar.setText(car.vehicle)
                    etKmGasoline.setText(car.kmGasolinePerLiter.toString())
                    etKmEthanol.setText(car.kmEthanolPerLiter.toString())
                    etPriceGasoline.setText(car.priceGasolinePerLiter.toString())
                    etPriceEthanol.setText(car.priceEthanolPerLiter.toString())
                    hideLoading()

                }
                is RequestState.Error -> {
                    hideLoading()
                }
                is RequestState.Loading -> {
                    showLoading("Aguarde um momento")
                }
            }
        }
    }

    private fun clearFields() {
        etCar.setText("")
        etKmGasoline.setText("")
        etKmEthanol.setText("")
        etPriceGasoline.setText("")
        etPriceEthanol.setText("")
    }
}
