package com.example.taskmanager.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.taskmanager.domain.model.Task
import kotlinx.coroutines.flow.Flow


enum class Priority(val value: Int){
    LOW(1), MEDIUM(2), HIGH(3)
}

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
//    val date: Date // ADD DATE FUNCTIONALITY
)

fun TaskEntity.toDomain() = Task(
    id,
    title,
    description,
    priority
)

fun Task.toEntity() = TaskEntity(
    id,
    title,
    description,
    priority
)

