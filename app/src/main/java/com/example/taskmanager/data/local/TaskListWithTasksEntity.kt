package com.example.taskmanager.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.domain.model.TaskListWithTasks

@Entity
data class TaskListWithTasksEntity (
    @Embedded val taskList: TaskList,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskListId"
    )
    val task: List<TaskEntity>
)

fun TaskListWithTasksEntity.toDomain() = TaskListWithTasks(
    taskList,
    task.map { it.toDomain() }
)

fun TaskListWithTasks.toEntity() = TaskListWithTasksEntity(
    taskList,
    task.map { it.toEntity() }
)