package com.tasky.agenda.data.schedulers.util

import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import com.tasky.agenda.data.schedulers.ReminderWorkSyncScheduler.Companion.CREATE_REMINDER
import com.tasky.agenda.data.schedulers.ReminderWorkSyncScheduler.Companion.DELETE_REMINDER
import com.tasky.agenda.data.schedulers.ReminderWorkSyncScheduler.Companion.REMINDER_WORK
import com.tasky.agenda.data.schedulers.ReminderWorkSyncScheduler.Companion.UPDATE_REMINDER
import com.tasky.agenda.data.workers.reminder.CreateReminderWorker
import com.tasky.agenda.data.workers.reminder.DeleteReminderWorker
import com.tasky.agenda.data.workers.reminder.UpdateReminderWorker
import com.tasky.agenda.domain.schedulers.ReminderSyncScheduler
import com.tasky.core.data.utils.setExponentialBackOffPolicy
import com.tasky.core.data.utils.setInputParameters
import com.tasky.core.data.utils.setRequiredNetworkConnectivity

fun WorkerType.toReminderOneTimeWorkRequest(
    reminderId: String
): OneTimeWorkRequest {
    return when (this) {
        WorkerType.CREATE -> {
            OneTimeWorkRequestBuilder<CreateReminderWorker>()
                .addTag("$CREATE_REMINDER$reminderId")
                .addTag(REMINDER_WORK)
                .addTag(CreateReminderWorker.TAG)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters { putString(CreateReminderWorker.REMINDER_ID, reminderId) }
                .build()
        }

        WorkerType.UPDATE -> {
            OneTimeWorkRequestBuilder<DeleteReminderWorker>()
                .addTag("$DELETE_REMINDER$reminderId")
                .addTag(REMINDER_WORK)
                .addTag(DeleteReminderWorker.TAG)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters {
                    putString(
                        DeleteReminderWorker.REMINDER_ID,
                        reminderId
                    )
                }.build()
        }

        WorkerType.DELETE -> {
            OneTimeWorkRequestBuilder<UpdateReminderWorker>()
                .addTag("$UPDATE_REMINDER$reminderId")
                .addTag(REMINDER_WORK)
                .addTag(UpdateReminderWorker.TAG)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters {
                    putString(
                        UpdateReminderWorker.REMINDER_ID,
                        reminderId
                    )
                }.build()
        }
    }
}