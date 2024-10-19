package com.tasky.agenda.data.mappers

import com.tasky.agenda.data.model.TaskEntity
import com.tasky.agenda.domain.model.Task

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt,
        time = time,
        isDone = isDone
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt,
        time = time,
        isDone = isDone
    )
}