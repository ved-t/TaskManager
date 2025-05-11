package com.example.taskmanager.di

import android.content.Context
import androidx.room.Insert
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanager.data.local.TaskDao
import com.example.taskmanager.data.local.TaskDatabase
import com.example.taskmanager.data.local.TaskDatabase_Impl
import com.example.taskmanager.data.repository.TaskRepositoryImpl
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repository.TaskRepository
import com.example.taskmanager.domain.usecase.DeleteTaskUseCase
import com.example.taskmanager.domain.usecase.GetTaskUseCase
import com.example.taskmanager.domain.usecase.InsertTaskUseCase
import com.example.taskmanager.domain.usecase.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    fun provideGetTaskUseCase(repository: TaskRepository): GetTaskUseCase = GetTaskUseCase(repository)

    @Provides
    fun provideInsertTaskUseCase(repository: TaskRepository): InsertTaskUseCase = InsertTaskUseCase(repository)

    @Provides
    fun provideUpdateTaskUseCase(repository: TaskRepository): UpdateTaskUseCase = UpdateTaskUseCase(repository)

    @Provides
    fun provideDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase = DeleteTaskUseCase(repository)

    @Provides
    fun provideTaskRepository(dao: TaskDao): TaskRepository = TaskRepositoryImpl(dao)


    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }
}