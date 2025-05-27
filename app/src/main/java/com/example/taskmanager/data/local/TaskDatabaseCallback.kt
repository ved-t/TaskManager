package com.example.taskmanager.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskDatabaseCallback @Inject constructor(
    private val taskDao: TaskDao
) : RoomDatabase.Callback(){
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch{
            taskDao.insertTaskList(TaskListEntity(listName = "Daily", isDefault = true))
            taskDao.insertTaskList(TaskListEntity(listName = "Weekly", isDefault = true))
            taskDao.insertTaskList(TaskListEntity(listName = "Monthly", isDefault = true))
        }
    }
}