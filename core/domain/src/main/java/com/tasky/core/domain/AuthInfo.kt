package com.tasky.core.domain

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val fullName: String,
    val userId: String,
)
