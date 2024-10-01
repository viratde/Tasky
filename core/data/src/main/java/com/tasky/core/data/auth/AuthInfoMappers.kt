package com.tasky.core.data.auth

import com.tasky.core.domain.AuthInfo

fun AuthInfo.toAuthInfoSerializable() = AuthInfoSerializable(
    fullName = fullName,
    refreshToken = refreshToken,
    accessToken = accessToken,
    userId = userId
)

fun AuthInfoSerializable.toAuthInfo() = AuthInfo(
    fullName = fullName,
    refreshToken = refreshToken,
    accessToken = accessToken,
    userId = userId
)