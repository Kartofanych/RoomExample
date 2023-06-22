package com.example.roomexample1.room

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
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "todolist")
data class TodoItem(
    @PrimaryKey val id:String,
    var text:String,
    var importance: Importance,
)

@Dao
interface TodoListDao{
    @Query("SELECT * FROM todolist")
    fun getAll(): Flow<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(todoItem: TodoItem)

    @Delete
    suspend fun delete(todoItem: TodoItem)

}

@Database(entities = [TodoItem::class], version = 1)
abstract class TodoListDatabase:RoomDatabase(){
    abstract val listDao:TodoListDao

    companion object{
        fun create(context: Context) = Room.databaseBuilder(
            context,
            TodoListDatabase::class.java,
            "todolist_database"
        ).build()
    }

}



enum class Importance{
    LOW,
    MIDDLE,
    HIGH
}
