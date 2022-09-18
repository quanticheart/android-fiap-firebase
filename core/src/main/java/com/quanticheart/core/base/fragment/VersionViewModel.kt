package com.quanticheart.core.base.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quanticheart.core.base.domain.models.RequestState
import com.quanticheart.core.base.domain.useCases.GetMinAppVersionUseCase
import kotlinx.coroutines.launch

class VersionViewModel(private val versionUseCase: GetMinAppVersionUseCase) : ViewModel() {
    var minVersionAppState = MutableLiveData<RequestState<Int>>()
    fun getMinVersion() {
        viewModelScope.launch {
            minVersionAppState.value = versionUseCase.getMinVersionApp()
        }
    }
}
