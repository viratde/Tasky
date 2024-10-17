package com.tasky.agenda.domain.repository.remote

import com.tasky.agenda.domain.model.Remainder
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result

interface RemoteRemainderDataSource {

    suspend fun update(remainder: Remainder): EmptyDataResult<DataError.Network>

    suspend fun create(remainder: Remainder): EmptyDataResult<DataError.Network>

    suspend fun delete(remainderId: String): EmptyDataResult<DataError.Network>

    suspend fun get(remainderId: String): Result<Remainder, DataError.Network>

}