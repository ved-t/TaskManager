package com.example.taskmanager.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.domain.model.TaskList


@Entity(
    tableName = "taskList"
)
data class TaskListEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val listName: String,
    val isDefault: Boolean
)

fun TaskListEntity.toDomain() = TaskList(
    id,
    listName,
    isDefault
)

fun TaskList.toEntity() = TaskListEntity(
    id,
    listName,
    isDefault
)