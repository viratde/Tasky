package com.tasky.test_utils.agenda.domain

import com.tasky.agenda.domain.model.Task
import java.time.ZonedDateTime
import java.util.UUID


fun fakeTask() = Task(
    id = UUID.randomUUID().toString(),
    title = "Complete Tasky Remote Data Source Implementation",
    description = "Implement all remote data sources using ktor and write tests for it",
    time = ZonedDateTime.now().toInstant().toEpochMilli(),
    remindAt = ZonedDateTime.now().minusMinutes(10).toInstant().toEpochMilli(),
    isDone = false
)

