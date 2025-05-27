package com.example.taskmanager.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.domain.model.TaskListWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE isComplete = 1 ORDER BY id ASC")
    fun getAllCompleteTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE isComplete = 0 ORDER BY id ASC")
    fun getAllIncompleteTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskList(taskListEntity: TaskListEntity)

    @Update
    suspend fun updateTaskList(taskListEntity: TaskListEntity)

    @Delete
    suspend fun deleteTaskList(taskListEntity: TaskListEntity)

    @Query("SELECT * FROM taskList ORDER BY id ASC")
    fun getAllTaskList(): Flow<List<TaskListEntity>>

    @Transaction
    @Query("SELECT * FROM tasklist ORDER BY id ASC")
    fun getAllTasksAndTaskLists(): Flow<List<TaskListWithTasksEntity>>

}