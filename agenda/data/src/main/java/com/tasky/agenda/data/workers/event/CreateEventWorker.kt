package com.tasky.agenda.data.workers.event

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tasky.agenda.data.dao.EventSyncDao
import com.tasky.agenda.data.mappers.toEvent
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.domain.data_sources.remote.RemoteEventDataSource
import com.tasky.core.data.utils.toWorkerResult
import com.tasky.core.domain.AuthInfoStorage

class CreateEventWorker(
    context: Context,
    private val params: WorkerParameters,
    private val authInfoStorage: AuthInfoStorage,
    private val eventSyncDao: EventSyncDao,
    private val remoteEventDataSource: RemoteEventDataSource
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if (runAttemptCount > 5) {
            return Result.failure()
        }

        val eventId = params.inputData.getString(EVENT_ID) ?: return Result.failure()
        val userId = authInfoStorage.get()?.userId ?: return Result.failure()
        val createRemoteSyncEntity = eventSyncDao.getEventPendingSyncById(
            eventId = eventId,
            userId = userId,
            syncType = SyncType.CREATE
        ) ?: return Result.failure()


        return when (val result =
            remoteEventDataSource.create(createRemoteSyncEntity.event.toEvent())) {
            is com.tasky.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.tasky.core.domain.util.Result.Success -> {
                eventSyncDao.deleteEventPendingSyncById(
                    eventId = eventId,
                    userId = userId,
                    syncType = SyncType.CREATE
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