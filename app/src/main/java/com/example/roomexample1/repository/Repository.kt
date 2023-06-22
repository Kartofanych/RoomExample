package com.example.roomexample1.repository

import com.example.roomexample1.room.TodoItem
import com.example.roomexample1.room.TodoListDatabase
import kotlinx.coroutines.flow.Flow

class Repository(
    db:TodoListDatabase
) {
    private val dao = db.listDao

    fun getList(): Flow<List<TodoItem>> = dao.getAll()

    suspend fun addItem(todoItem: TodoItem){
        dao.add(todoItem)
    }

    suspend fun deleteItem(todoItem: TodoItem){
        dao.delete(todoItem)
    }


}