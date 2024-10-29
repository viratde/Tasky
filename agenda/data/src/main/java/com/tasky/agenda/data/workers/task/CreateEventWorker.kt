package com.tasky.agenda.data.workers.task

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tasky.agenda.data.dao.TaskDeleteSyncDao
import com.tasky.agenda.data.dao.TaskSyncDao
import com.tasky.core.domain.AuthInfoStorage
import kotlinx.coroutines.CoroutineScope

class CreateEventWorker(
    context: Context,
    private val params: WorkerParameters,
    private val applicationScope: CoroutineScope,
    private val authInfoStorage: AuthInfoStorage,
    private val taskSyncDao: TaskSyncDao,
    private val taskDeleteSyncDao: TaskDeleteSyncDao
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }

    companion object {
        const val EVENT_ID = "EVENT_ID"
    }
}