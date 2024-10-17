package com.tasky.agenda.domain.repository.remote

import com.tasky.agenda.domain.model.Task
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result

interface RemoteTaskDataSource {

    suspend fun update(task: Task): EmptyDataResult<DataError.Network>

    suspend fun create(task: Task): EmptyDataResult<DataError.Network>

    suspend fun delete(taskId: String): EmptyDataResult<DataError.Network>

    suspend fun get(taskId: String): Result<Task, DataError.Network>

}