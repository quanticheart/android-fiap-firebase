package com.quanticheart.core.utils.featuretoggle

import android.content.Context

interface FeatureToggleListener {
    fun onEnabled()
    fun onInvisible()
    fun onDisabled(clickListener: (Context) -> Unit)
}
