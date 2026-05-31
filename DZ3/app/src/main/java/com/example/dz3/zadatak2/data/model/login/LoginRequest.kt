package com.example.dz3.zadatak2.data.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("username") val email: String,
    @SerialName("password") val password: String
)