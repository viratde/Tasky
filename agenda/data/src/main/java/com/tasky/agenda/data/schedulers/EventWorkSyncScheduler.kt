package com.tasky.agenda.data.schedulers

import android.content.Context
import androidx.work.WorkManager
import androidx.work.await
import com.tasky.agenda.data.dao.EventDeleteSyncDao
import com.tasky.agenda.data.dao.EventSyncDao
import com.tasky.agenda.data.mappers.toEventEntity
import com.tasky.agenda.data.model.EventDeleteSyncEntity
import com.tasky.agenda.data.model.EventSyncEntity
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.data.schedulers.TaskWorkSyncScheduler.Companion.TASK_WORK
import com.tasky.agenda.data.schedulers.util.WorkerType
import com.tasky.agenda.data.schedulers.util.toEventOneTimeWorkRequest
import com.tasky.agenda.domain.schedulers.EventSyncScheduler
import com.tasky.core.domain.AuthInfoStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class EventWorkSyncScheduler(
    context: Context,
    private val authInfoStorage: AuthInfoStorage,
    private val eventSyncDao: EventSyncDao,
    private val eventDeleteSyncDao: EventDeleteSyncDao,
    private val applicationScope: CoroutineScope
) : EventSyncScheduler {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun sync(syncType: EventSyncScheduler.SyncType) {
        when (syncType) {
            is EventSyncScheduler.SyncType.CreateEventSync -> {
                scheduleCreateEventWorker(syncType)
            }

            is EventSyncScheduler.SyncType.UpdateEventSync -> {
                scheduleUpdateEventWorker(syncType)
            }

            is EventSyncScheduler.SyncType.DeleteEventSync -> {
                scheduleDeleteEventWorker(syncType)
            }
        }
    }

    override suspend fun cancelAllSyncs() {
        workManager
            .cancelAllWorkByTag(EVENT_WORK)
            .await()
    }

    private suspend fun scheduleCreateEventWorker(
        sync: EventSyncScheduler.SyncType.CreateEventSync,
    ) {
        val userId = authInfoStorage.get()?.userId ?: return
        val eventSyncEntity = EventSyncEntity(
            eventId = sync.event.id,
            event = sync.event.toEventEntity(),
            userId = userId,
            syncType = SyncType.CREATE
        )
        eventSyncDao.upsertEventPendingSync(eventSyncEntity)
        applicationScope.launch {
            workManager.enqueue(WorkerType.CREATE.toEventOneTimeWorkRequest(sync.event.id)).await()
        }.join()
    }

    private suspend fun scheduleUpdateEventWorker(
        sync: EventSyncScheduler.SyncType.UpdateEventSync
    ) {
        val userId = authInfoStorage.get()?.userId ?: return
        val eventSyncEntity = EventSyncEntity(
            eventId = sync.event.id,
            event = sync.event.toEventEntity(),
            userId = userId,
            syncType = SyncType.UPDATE
        )
        val pendingCreateEventSync = eventSyncDao.getEventPendingSyncById(
            eventId = sync.event.id,
            userId = userId,
            syncType = SyncType.CREATE
        )
        applicationScope.launch {
            if (pendingCreateEventSync != null) {
                workManager.cancelAllWorkByTag("$CREATE_EVENT${pendingCreateEventSync.eventId}")
                    .await()
                eventSyncDao.upsertEventPendingSync(
                    eventSyncEntity.copy(
                        syncType = SyncType.CREATE
                    )
                )
                workManager.enqueue(WorkerType.CREATE.toEventOneTimeWorkRequest(sync.event.id))
                    .await()
            } else {
                eventSyncDao.upsertEventPendingSync(eventSyncEntity)
                workManager.enqueue(WorkerType.UPDATE.toEventOneTimeWorkRequest(sync.event.id))
                    .await()
            }
        }.join()
    }

    private suspend fun scheduleDeleteEventWorker(
        sync: EventSyncScheduler.SyncType.DeleteEventSync
    ) {
        val userId = authInfoStorage.get()?.userId ?: return
        val eventDeleteSyncEntity = EventDeleteSyncEntity(
            eventId = sync.eventId,
            userId = userId,
        )
        val pendingCreateEventSync = eventSyncDao.getEventPendingSyncById(
            eventId = sync.eventId,
            userId = userId,
            syncType = SyncType.CREATE
        )
        val pendingUpdateEventSync = eventSyncDao.getEventPendingSyncById(
            eventId = sync.eventId,
            userId = userId,
            syncType = SyncType.UPDATE
        )
        applicationScope.launch {
            workManager.apply {
                if (pendingCreateEventSync != null) {
                    cancelAllWorkByTag("$CREATE_EVENT${pendingCreateEventSync.eventId}").await()
                    eventSyncDao.deleteEventPendingSyncById(
                        eventId = sync.eventId,
                        userId = userId,
                        syncType = SyncType.CREATE
                    )
                }
                if (pendingUpdateEventSync != null) {
                    cancelAllWorkByTag("$UPDATE_EVENT${pendingUpdateEventSync.eventId}").await()
                    eventSyncDao.deleteEventPendingSyncById(
                        eventId = sync.eventId,
                        userId = userId,
                        syncType = SyncType.UPDATE
                    )
                }
                if (pendingCreateEventSync == null) {
                    eventDeleteSyncDao.upsertEventDeletePendingSync(eventDeleteSyncEntity)
                    enqueue(WorkerType.DELETE.toEventOneTimeWorkRequest(eventDeleteSyncEntity.eventId))
                        .await()
                }
            }
        }.join()
    }

    companion object {
        const val CREATE_EVENT = "create_event"
        const val UPDATE_EVENT = "update_event"
        const val DELETE_EVENT = "delete_event"
        const val EVENT_WORK = "event_work"
    }
}