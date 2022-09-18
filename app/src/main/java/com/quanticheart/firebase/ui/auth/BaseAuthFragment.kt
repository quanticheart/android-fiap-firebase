package com.quanticheart.firebase.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.fragment.BaseFragment
import com.quanticheart.firebase.R
import org.koin.androidx.viewmodel.ext.android.viewModel

const val NAVIGATION_KEY = "NAV_KEY"

abstract class BaseAuthFragment : BaseFragment() {

    private val baseAuthViewModel: BaseAuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        registerObserver()

        baseAuthViewModel.getUserLogged()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun registerObserver() {

        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RequestState.Loading -> {
                    showLoading()
                }

                is RequestState.Success -> {
                    hideLoading()
                }

                is RequestState.Error -> {
                    hideLoading()

                    findNavController().navigate(
                        R.id.login_nav_graph, bundleOf(
                            NAVIGATION_KEY to findNavController().currentDestination?.id
                        )
                    )
                }
            }
        }
    }
}
