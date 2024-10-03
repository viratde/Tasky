package com.tasky.auth.presentation.common

import com.tasky.auth.domain.AuthRepository
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import kotlinx.coroutines.delay

class FakeAuthRepository : AuthRepository {

    var errorToReturn: DataError.Network? = null

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        delay(1000L)
        return if (errorToReturn == null) {
            Result.Success(Unit)
        } else {
            Result.Error(errorToReturn!!)
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        delay(1000L)
        return if (errorToReturn == null) {
            Result.Success(Unit)
        } else {
            Result.Error(errorToReturn!!)
        }
    }

}