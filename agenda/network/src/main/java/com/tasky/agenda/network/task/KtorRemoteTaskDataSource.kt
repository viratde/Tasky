package com.tasky.agenda.network.task

import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.domain.data_sources.remote.RemoteTaskDataSource
import com.tasky.agenda.network.common.dtos.TaskDto
import com.tasky.agenda.network.common.mappers.toTask
import com.tasky.agenda.network.common.mappers.toTaskDto
import com.tasky.core.data.networking.delete
import com.tasky.core.data.networking.get
import com.tasky.core.data.networking.post
import com.tasky.core.data.networking.put
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.mapData
import io.ktor.client.HttpClient

class KtorRemoteTaskDataSource(
    private val httpClient: HttpClient
) : RemoteTaskDataSource {

    override suspend fun update(task: Task): EmptyDataResult<DataError.Network> {
        return httpClient.put(
            route = "/task",
            body = task.toTaskDto()
        )
    }

    override suspend fun create(task: Task): EmptyDataResult<DataError.Network> {
        return httpClient.post(
            route = "/task",
            body = task.toTaskDto()
        )
    }

    override suspend fun delete(taskId: String): EmptyDataResult<DataError.Network> {
        return httpClient.delete(
            route = "/task",
            queryParameters = mapOf(
                "taskId" to taskId
            )
        )
    }

    override suspend fun get(taskId: String): Result<Task, DataError.Network> {
        return httpClient.get<TaskDto>(
            route = "/task",
            queryParameters = mapOf(
                "taskId" to taskId
            )
        ).mapData { it.toTask() }
    }

}