package com.example.roomexample1.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.roomexample1.room.TodoItem
import com.example.roomexample1.room.TodoListDatabase
import kotlinx.coroutines.flow.Flow

class Repository(
    db: TodoListDatabase
) {
    private val dao = db.listDao

    fun getList(): Flow<List<TodoItem>> = dao.getAll()
    fun getTodoList():Flow<List<TodoItem>> = dao.getToDo()

    fun getItem(itemId: String): Flow<TodoItem> = dao.getItem(itemId)

    suspend fun addItem(todoItem: TodoItem) {
        dao.add(todoItem)
    }

    suspend fun deleteItem(todoItem: TodoItem) {
        dao.delete(todoItem)
    }

    suspend fun changeItem(newItem: TodoItem) {
        dao.update(newItem)
    }

    suspend fun changeDone(id: String, done: Boolean) {
        dao.updateDone(id, done)
    }


}