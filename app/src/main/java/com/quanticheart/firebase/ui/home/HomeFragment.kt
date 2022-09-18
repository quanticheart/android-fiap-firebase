package com.quanticheart.firebase.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.quanticheart.core.base.domain.models.DashboardItem
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.extensions.startDeeplink
import com.quanticheart.firebase.R
import com.quanticheart.firebase.ui.auth.BaseAuthFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_home

    private lateinit var rvHomeDashboard: RecyclerView
    private lateinit var tvHomeHelloUser: TextView
    private lateinit var tvSubTitleSignUp: TextView
    private lateinit var addBtn: FloatingActionButton

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()
        setUpView(view)

        registerObserver()
        homeViewModel.getDashboardMenu()
    }

    private fun setUpView(view: View) {
        rvHomeDashboard = view.findViewById(R.id.rvHomeDashboard)
        tvHomeHelloUser = view.findViewById(R.id.tvHomeHelloUser)
        tvSubTitleSignUp = view.findViewById(R.id.tvSubTitleSignUp)
        addBtn = view.findViewById(R.id.fab)
    }

    private fun registerObserver() {
        homeViewModel.headerState.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Loading -> {
                    tvHomeHelloUser.isVisible = true
                    tvSubTitleSignUp.isVisible = true

                    tvHomeHelloUser.text = getString(R.string.label_user_name)
                    tvSubTitleSignUp.text = ""
                }

                is RequestState.Success -> {

                    tvHomeHelloUser.isVisible = true
                    tvSubTitleSignUp.isVisible = true

                    val (title, subTitle, userName) = it.data
                    tvHomeHelloUser.text = String.format(title, userName)
                    tvSubTitleSignUp.text = subTitle
                    hideLoading()
                }

                is RequestState.Error -> {
                    tvHomeHelloUser.isVisible = false
                    tvSubTitleSignUp.isVisible = false
                }
            }

        }
        homeViewModel.dashboardItemsState.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }

                is RequestState.Success -> {
                    setUpMenu(it.data)
                    hideLoading()
                }

                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
            }
        }

        addBtn.setOnClickListener {
            findNavController().navigate(R.id.betterFuelFragment)
        }
    }

    private fun setUpMenu(items: List<DashboardItem>) {
        rvHomeDashboard.adapter = HomeAdapter(items, this::clickItem)
    }

    private fun clickItem(item: DashboardItem) {
        item.onDisabledListener.let {
            it?.invoke(requireContext())
        }

        if (item.onDisabledListener == null) {
            when (item.feature) {
                "SIGN_OUT" -> {
                    //chamar o metodo de logout
                }
                "ETHANOL_OR_GASOLINE" -> {
                    startDeeplink("${item.action.deeplink}?id=${homeViewModel.userLogged?.id}")
                }
                else -> {
                    startDeeplink(item.action.deeplink)
                }
            }
        }
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
