package com.example.roomexample1.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.roomexample1.room.TodoItem

class TodoListAdapter(private val callbacks: AdapterClickCallbacks):ListAdapter<TodoItem, ListViewHolder>(TodoItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder.create(parent)

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.onBind(getItem(position), callbacks)
    }
}

interface AdapterClickCallbacks{
    fun onItemClick(id:String)

    fun onCheckClick(id:String, done:Boolean)

}