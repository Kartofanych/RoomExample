package com.example.roomexample1.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.roomexample1.room.TodoItem

class TodoItemDiffCallback : DiffUtil.ItemCallback<TodoItem>(){
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean = oldItem == newItem

}