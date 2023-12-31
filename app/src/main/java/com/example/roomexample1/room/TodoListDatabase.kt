package com.example.roomexample1.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItem::class], version = 2)
abstract class TodoListDatabase: RoomDatabase(){
    abstract val listDao: TodoListDao

    companion object{
        fun create(context: Context) = Room.databaseBuilder(
            context,
            TodoListDatabase::class.java,
            "todolist_database"
        ).fallbackToDestructiveMigration().build()
    }

}