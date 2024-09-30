package com.tasky.core.data.networking

data class AccessTokenRequest(
    val refreshToken: String,
    val userId: String
)
