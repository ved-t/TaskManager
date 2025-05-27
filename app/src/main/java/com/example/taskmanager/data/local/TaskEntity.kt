package com.example.taskmanager.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.taskmanager.domain.model.Task


enum class Priority(val value: Int){
    LOW(1), MEDIUM(2), HIGH(3)
}

enum class Repeat(val value: Int?){
    None(null),
    Daily(1),
    Weekly(7),
    Monthly(30),
    Custom(null)
}

@Entity(tableName = "tasks", foreignKeys = [ForeignKey(
    entity = TaskListEntity::class,
    parentColumns = ["id"],
    childColumns = ["taskListId"]
)],
    indices = [Index("taskListId")]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val dueDate: Long,
    val dueTime: Int,
    val repeat: Repeat,
    val isRepeating: Boolean,
    val isComplete: Boolean,

    val taskListId: Int?
)

fun TaskEntity.toDomain() = Task(
    id,
    title,
    description,
    priority,
    dueDate,
    dueTime,
    repeat,
    isRepeating,
    isComplete,
    taskListId
)

fun Task.toEntity() = TaskEntity(
    id,
    title,
    description,
    priority,
    dueDate,
    dueTime,
    repeat,
    isRepeating,
    isComplete,
    taskListId
)

