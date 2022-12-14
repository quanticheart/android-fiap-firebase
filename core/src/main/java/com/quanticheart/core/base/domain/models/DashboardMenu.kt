package com.quanticheart.core.base.domain.models

import android.content.Context
import com.quanticheart.firebase.domain.entity.enums.FeatureToggleState

data class DashboardMenu(
    val title: String,
    val subTitle: String,
    val items: List<DashboardItem>
)

data class DashboardItem(
    val feature: String,
    val image: String,
    val label: String,
    val status: FeatureToggleState,
    val action: DashboardAction,
    var onDisabledListener: ((Context) -> Unit)?
)

data class DashboardAction(
    val deeplink: String,
    val params: HashMap<String, Any>
)