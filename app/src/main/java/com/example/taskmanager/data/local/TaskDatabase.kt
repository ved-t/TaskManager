package com.example.taskmanager.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TaskEntity::class], version = 1, exportSchema = true )
abstract class TaskDatabase: RoomDatabase(){
    abstract fun taskDao(): TaskDao
}