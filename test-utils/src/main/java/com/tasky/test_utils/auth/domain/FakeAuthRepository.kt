package com.tasky.test_utils.auth.domain

import com.tasky.auth.domain.AuthRepository
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result

class FakeAuthRepository : AuthRepository {
    var errorToReturn: DataError.Network? = null

    override suspend fun register(
        fullName: String,
        email: String,
        password: String,
    ): EmptyDataResult<DataError.Network> {
        return if (errorToReturn == null) {
            Result.Success(Unit)
        } else {
            Result.Error(errorToReturn!!)
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): EmptyDataResult<DataError.Network> {
        return if (errorToReturn == null) {
            Result.Success(Unit)
        } else {
            Result.Error(errorToReturn!!)
        }
    }
}
