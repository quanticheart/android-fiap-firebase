package com.quanticheart.core.base.domain.models.user

data class NewUser(
    val name: String,
    val email: String,
    val phone: String,
    val password: String
)
