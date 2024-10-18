package com.tasky.agenda.network.task

import com.tasky.agenda.domain.model.Task

fun TaskDto.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
        isDone = isDone
    )
}

fun Task.toTaskDto(): TaskDto {
    return TaskDto(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
        isDone = isDone
    )
}