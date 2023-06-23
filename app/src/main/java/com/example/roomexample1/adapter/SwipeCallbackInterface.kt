package com.example.roomexample1.adapter

import com.example.roomexample1.room.TodoItem

interface SwipeCallbackInterface {
    fun onDelete(todoItem: TodoItem)
    fun onChangeDone(todoItem: TodoItem)
}