package com.tasky.core.domain

interface AuthInfoStorage {
    suspend fun set(authInfo: AuthInfo?)

    suspend fun get(): AuthInfo?
}
