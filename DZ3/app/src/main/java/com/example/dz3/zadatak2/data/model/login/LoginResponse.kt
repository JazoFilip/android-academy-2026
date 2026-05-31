package com.example.dz3.zadatak2.data.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    @SerialName("token") val token: String
)