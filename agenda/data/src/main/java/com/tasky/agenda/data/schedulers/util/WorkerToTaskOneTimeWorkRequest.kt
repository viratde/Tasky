package com.tasky.agenda.data.schedulers.util

import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import com.tasky.agenda.data.schedulers.TaskWorkSyncScheduler.Companion.CREATE_TASK
import com.tasky.agenda.data.schedulers.TaskWorkSyncScheduler.Companion.DELETE_TASK
import com.tasky.agenda.data.schedulers.TaskWorkSyncScheduler.Companion.TASK_WORK
import com.tasky.agenda.data.schedulers.TaskWorkSyncScheduler.Companion.UPDATE_TASK
import com.tasky.agenda.data.workers.task.CreateTaskWorker
import com.tasky.agenda.data.workers.task.DeleteTaskWorker
import com.tasky.agenda.data.workers.task.UpdateTaskWorker
import com.tasky.core.data.utils.setExponentialBackOffPolicy
import com.tasky.core.data.utils.setInputParameters
import com.tasky.core.data.utils.setRequiredNetworkConnectivity

fun WorkerType.toTaskOneTimeWorkRequest(
    taskId: String
): OneTimeWorkRequest {
    return when (this) {
        WorkerType.CREATE -> {
            OneTimeWorkRequestBuilder<CreateTaskWorker>()
                .addTag("$CREATE_TASK$taskId")
                .addTag(TASK_WORK)
                .addTag(CreateTaskWorker.TAG)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters { putString(CreateTaskWorker.TASK_ID, taskId) }
                .build()
        }

        WorkerType.UPDATE -> {
            OneTimeWorkRequestBuilder<DeleteTaskWorker>()
                .addTag("$DELETE_TASK${taskId}")
                .addTag(TASK_WORK)
                .addTag(DeleteTaskWorker.TAG)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters {
                    putString(
                        DeleteTaskWorker.TASK_ID,
                        taskId
                    )
                }
                .build()
        }

        WorkerType.DELETE -> {
            OneTimeWorkRequestBuilder<UpdateTaskWorker>()
                .addTag("$UPDATE_TASK$taskId")
                .addTag(TASK_WORK)
                .addTag(UpdateTaskWorker.TAG)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters {
                    putString(
                        UpdateTaskWorker.TASK_ID,
                        taskId
                    )
                }
                .build()
        }
    }
}