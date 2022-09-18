package com.quanticheart.core.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.quanticheart.core.R
import com.quanticheart.core.base.domain.models.RequestState
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragment : Fragment() {

    abstract val layout: Int
    private lateinit var loadingView: View

    private val versionViewModel: VersionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val screenRootView = FrameLayout(requireContext())
        val screenView = inflater.inflate(layout, container, false)

        loadingView = inflater.inflate(R.layout.include_loading, container, false)

        screenRootView.addView(screenView)
        screenRootView.addView(loadingView)
        return screenRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserver()
    }

    private fun registerObserver() {
        versionViewModel.minVersionAppState.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    hideLoading()
                    if (it.data > 1) {
                        startUpdateApp()
                    }
                }
                is RequestState.Error -> {
                    hideLoading()
                }
            }
        }
    }

    private fun startUpdateApp() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.updateAppFragment, true)
            .build()

        findNavController().setGraph(R.navigation.update_app_nav_graph)
        findNavController().navigate(R.id.updateAppFragment, null, navOptions)
    }

    override fun onResume() {
        super.onResume()
        versionViewModel.getMinVersion()
    }


    fun showLoading(message: String = "Processando a requisição") {
        loadingView.visibility = View.VISIBLE

        if (message.isNotEmpty())
            loadingView.findViewById<TextView>(R.id.tvLoading).text = message

    }

    fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
