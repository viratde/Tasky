package com.tasky.agenda.data.workers.event

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tasky.agenda.data.dao.EventDeleteSyncDao
import com.tasky.agenda.domain.data_sources.remote.RemoteEventDataSource
import com.tasky.core.data.utils.toWorkerResult
import com.tasky.core.domain.AuthInfoStorage

class DeleteEventWorker(
    context: Context,
    private val params: WorkerParameters,
    private val authInfoStorage: AuthInfoStorage,
    private val eventDeleteSyncDao: EventDeleteSyncDao,
    private val remoteEventDataSource: RemoteEventDataSource
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if (runAttemptCount > 5) {
            return Result.failure()
        }

        val eventId = params.inputData.getString(EVENT_ID) ?: return Result.failure()
        val userId = authInfoStorage.get()?.userId ?: return Result.failure()
        val deleteEventSyncEntity = eventDeleteSyncDao.getEventDeletePendingSyncById(
            eventId = eventId,
            userId = userId
        ) ?: return Result.failure()

        return when (val result =
            remoteEventDataSource.delete(deleteEventSyncEntity.eventId)) {
            is com.tasky.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.tasky.core.domain.util.Result.Success -> {
                eventDeleteSyncDao.deleteEventDeletePendingSyncById(
                    eventId = eventId,
                    userId = userId,
                )
                Result.success()
            }
        }

    }

    companion object {
        const val EVENT_ID = "event_id"
        const val TAG = "create_event_work"
    }
}