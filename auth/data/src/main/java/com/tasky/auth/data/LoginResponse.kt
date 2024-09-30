package com.tasky.auth.data

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val fullName: String,
    val userId: String,
    val accessTokenExpirationTimestamp: Long
)
