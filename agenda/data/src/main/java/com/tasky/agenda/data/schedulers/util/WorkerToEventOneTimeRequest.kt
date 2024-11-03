package com.tasky.agenda.data.schedulers.util

import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import com.tasky.agenda.data.schedulers.EventWorkSyncScheduler.Companion.CREATE_EVENT
import com.tasky.agenda.data.schedulers.EventWorkSyncScheduler.Companion.DELETE_EVENT
import com.tasky.agenda.data.schedulers.EventWorkSyncScheduler.Companion.EVENT_WORK
import com.tasky.agenda.data.schedulers.EventWorkSyncScheduler.Companion.UPDATE_EVENT
import com.tasky.agenda.data.workers.event.CreateEventWorker
import com.tasky.agenda.data.workers.event.DeleteEventWorker
import com.tasky.agenda.data.workers.event.UpdateEventWorker
import com.tasky.core.data.utils.setExponentialBackOffPolicy
import com.tasky.core.data.utils.setInputParameters
import com.tasky.core.data.utils.setRequiredNetworkConnectivity

fun WorkerType.toEventOneTimeWorkRequest(
    eventId: String
): OneTimeWorkRequest {
    return when (this) {
        WorkerType.CREATE -> {
            OneTimeWorkRequestBuilder<CreateEventWorker>()
                .addTag("$CREATE_EVENT$eventId")
                .addTag(EVENT_WORK)
                .addTag(CreateEventWorker.TAG)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters { putString(CreateEventWorker.EVENT_ID, eventId) }
                .build()
        }

        WorkerType.UPDATE -> {
            OneTimeWorkRequestBuilder<UpdateEventWorker>()
                .addTag("$UPDATE_EVENT$eventId")
                .addTag(EVENT_WORK)
                .addTag(UpdateEventWorker.TAG)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters {
                    putString(
                        UpdateEventWorker.EVENT_ID,
                        eventId
                    )
                }.build()
        }

        WorkerType.DELETE -> {
            OneTimeWorkRequestBuilder<DeleteEventWorker>()
                .addTag("$DELETE_EVENT$eventId")
                .addTag(EVENT_WORK)
                .addTag(DeleteEventWorker.TAG)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters {
                    putString(
                        DeleteEventWorker.EVENT_ID,
                        eventId
                    )
                }.build()
        }
    }
}