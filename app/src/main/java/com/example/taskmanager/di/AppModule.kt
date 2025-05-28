package com.example.taskmanager.di

import android.content.Context
import androidx.room.Room
import com.example.taskmanager.core.util.LocalDateConverter
import com.example.taskmanager.core.util.LocalTimeConverter
import com.example.taskmanager.core.util.TrimWhiteSpaces
import com.example.taskmanager.data.local.TaskDao
import com.example.taskmanager.data.local.TaskDatabase
import com.example.taskmanager.data.local.TaskDatabaseCallback
import com.example.taskmanager.data.repository.TaskRepositoryImpl
import com.example.taskmanager.domain.repository.TaskRepository
import com.example.taskmanager.domain.usecase.CalculateNextDueDateUseCase
import com.example.taskmanager.domain.usecase.CompleteTaskUseCase
import com.example.taskmanager.domain.usecase.FilterCompleteTaskUseCase
import com.example.taskmanager.domain.usecase.FilterIncomepleteTaskUseCase
import com.example.taskmanager.domain.usecase.TaskListWithTaskUseCase
import com.example.taskmanager.domain.usecase.database.DeleteTaskUseCase
import com.example.taskmanager.domain.usecase.database.GetIncompleteTaskUseCase
import com.example.taskmanager.domain.usecase.database.InsertTaskUseCase
import com.example.taskmanager.domain.usecase.converters.LocalDateToMillisUseCase
import com.example.taskmanager.domain.usecase.converters.LocalTimeToMinutesUseCase
import com.example.taskmanager.domain.usecase.converters.MillisToLocalDateUseCase
import com.example.taskmanager.domain.usecase.converters.MinutesToLocalTimeUseCase
import com.example.taskmanager.domain.usecase.database.DeleteTaskListUseCase
import com.example.taskmanager.domain.usecase.database.GetCompleteTaskUseCase
import com.example.taskmanager.domain.usecase.database.GetTaskListAndTasksUseCase
import com.example.taskmanager.domain.usecase.database.GetTaskListUseCase
import com.example.taskmanager.domain.usecase.database.InsertTaskListUseCase
import com.example.taskmanager.domain.usecase.database.UpdateTaskListUseCase
import com.example.taskmanager.domain.usecase.utils.TrimWhiteSpacesUseCase
import com.example.taskmanager.domain.usecase.database.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    fun provideGetIncompleteTaskUseCase(repository: TaskRepository): GetIncompleteTaskUseCase = GetIncompleteTaskUseCase(repository)

    @Provides
    fun provideGetCompleteTaskUseCase(repository: TaskRepository): GetCompleteTaskUseCase = GetCompleteTaskUseCase(repository)

    @Provides
    fun provideInsertTaskUseCase(repository: TaskRepository): InsertTaskUseCase = InsertTaskUseCase(repository)

    @Provides
    fun provideUpdateTaskUseCase(repository: TaskRepository): UpdateTaskUseCase = UpdateTaskUseCase(repository)

    @Provides
    fun provideDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase = DeleteTaskUseCase(repository)

    @Provides
    fun provideGetTaskListUseCase(taskRepository: TaskRepository): GetTaskListUseCase = GetTaskListUseCase(taskRepository)

    @Provides
    fun provideInsertTaskListUseCase(taskRepository: TaskRepository): InsertTaskListUseCase = InsertTaskListUseCase(taskRepository)

    @Provides
    fun provideUpdateTaskListUseCase(taskRepository: TaskRepository): UpdateTaskListUseCase = UpdateTaskListUseCase(taskRepository)

    @Provides
    fun provideDeleteTaskListUseCase(taskRepository: TaskRepository): DeleteTaskListUseCase = DeleteTaskListUseCase(taskRepository)

    @Provides
    fun provideGetTaskListAndTasks(taskRepository: TaskRepository): GetTaskListAndTasksUseCase = GetTaskListAndTasksUseCase(taskRepository)


    @Provides
    fun provideTrimWhiteSpaceUseCase(trimWhiteSpaces: TrimWhiteSpaces): TrimWhiteSpacesUseCase = TrimWhiteSpacesUseCase(trimWhiteSpaces)

    @Provides
    fun provideTrimWhiteSpaces(): TrimWhiteSpaces = TrimWhiteSpaces()

    @Provides
    fun provideLocalDateToMillisUseCase(localDateConverter: LocalDateConverter): LocalDateToMillisUseCase = LocalDateToMillisUseCase(localDateConverter)

    @Provides
    fun provideMillisToLocalDateUseCase(localDateConverter: LocalDateConverter): MillisToLocalDateUseCase = MillisToLocalDateUseCase(localDateConverter)

    @Provides
    fun provideLocalDateConverter(): LocalDateConverter = LocalDateConverter()

    @Provides
    fun provideLocalTimeToMinutesUseCase(localTimeConverter: LocalTimeConverter): LocalTimeToMinutesUseCase = LocalTimeToMinutesUseCase(localTimeConverter)

    @Provides
    fun provideMinutesToLocalTimeUseCase(localTimeConverter: LocalTimeConverter): MinutesToLocalTimeUseCase = MinutesToLocalTimeUseCase((localTimeConverter))

    @Provides
    fun provideLocalTimeConverter(): LocalTimeConverter = LocalTimeConverter()

    @Provides
    fun provideCalculateNextDueDateUseCase(): CalculateNextDueDateUseCase = CalculateNextDueDateUseCase()

    @Provides
    fun provideTaskListWithTaskUseCase(): TaskListWithTaskUseCase = TaskListWithTaskUseCase()

    @Provides
    fun provideFilterIncompleteCompleteTaskUseCase(): FilterIncomepleteTaskUseCase = FilterIncomepleteTaskUseCase()

    @Provides
    fun provideFilterCompleteTaskUseCase(): FilterCompleteTaskUseCase = FilterCompleteTaskUseCase()

    @Provides
    fun provideCompleteTaskUseCase(
        calculateNextDueDateUseCase: CalculateNextDueDateUseCase,
        localDateToMillisUseCase: LocalDateToMillisUseCase,
        longToLocalDateUseCase: MillisToLocalDateUseCase
    ): CompleteTaskUseCase {
        return CompleteTaskUseCase(
            calculateNextDueDateUseCase,
            localDateToMillisUseCase,
            longToLocalDateUseCase)
    }

    @Provides
    fun provideTaskRepository(dao: TaskDao): TaskRepository = TaskRepositoryImpl(dao)

    @Provides
    fun provideTaskDatabaseCallback(taskDao: TaskDao): TaskDatabaseCallback = TaskDatabaseCallback(taskDao)

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
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

