package com.example.roomexample1.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao{
    @Query("SELECT * FROM todolist")
    fun getAll(): Flow<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(todoItem: TodoItem)

    @Delete
    suspend fun delete(todoItem: TodoItem)

}