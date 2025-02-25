package com.example.taskmanager

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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class
import kotlinx.coroutines.flow.Flow
import java.util.Date


enum class Priority(val value: Int){
    ONE(1), TWO(2), THREE(3)
}

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
//    val date: Date // ADD DATE FUNCTIONALITY
)

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAllTasks(): Flow<List<Task>>
}

@Database(entities = [Task::class], version = 1, exportSchema = true )
abstract class TaskDatabase: RoomDatabase(){
    abstract fun taskDoa(): TaskDao
    private var INSTANCE: TaskDatabase? = null

    fun getDatabase(context: Context): TaskDatabase{
        return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}