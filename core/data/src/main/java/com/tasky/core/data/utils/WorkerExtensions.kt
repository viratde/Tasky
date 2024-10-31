package com.tasky.core.data.utils

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import java.util.concurrent.TimeUnit

fun OneTimeWorkRequest.Builder.setRequiredNetworkConnectivity(): OneTimeWorkRequest.Builder {
    return this.setConstraints(
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    )
}

fun OneTimeWorkRequest.Builder.setExponentialBackOffPolicy(duration: Long): OneTimeWorkRequest.Builder {
    return this.setBackoffCriteria(
        backoffPolicy = BackoffPolicy.EXPONENTIAL,
        backoffDelay = duration,
        timeUnit = TimeUnit.MILLISECONDS
    )
}

fun OneTimeWorkRequest.Builder.setInputParameters(block: Data.Builder.() -> Unit): OneTimeWorkRequest.Builder {
    return this.setInputData(
        Data.Builder()
            .apply(block)
            .build()
    )
}