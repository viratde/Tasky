package com.tasky.auth.domain

import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult

interface AuthRepository {
    suspend fun register(
        fullName: String,
        email: String,
        password: String,
    ): EmptyDataResult<DataError.Network>

    suspend fun login(
        email: String,
        password: String,
    ): EmptyDataResult<DataError.Network>
}
