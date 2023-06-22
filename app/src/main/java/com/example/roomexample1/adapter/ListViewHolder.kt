package com.example.roomexample1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample1.R
import com.example.roomexample1.databinding.ElementListBinding
import com.example.roomexample1.room.Importance
import com.example.roomexample1.room.TodoItem

class ListViewHolder(private val binding: ElementListBinding): RecyclerView.ViewHolder(binding.root){

    private var todoItem: TodoItem? = null

    fun onBind(todoItem: TodoItem){
        this.todoItem = todoItem
        views {
            text.text = todoItem.text
            when(todoItem.importance) {
                Importance.LOW -> {
                    importance.visibility = View.VISIBLE
                    importance.setImageResource(R.drawable.ic_low)
                }
                Importance.MIDDLE -> {
                    importance.visibility = View.INVISIBLE
                }
                Importance.HIGH -> {
                    importance.visibility = View.VISIBLE
                    importance.setImageResource(R.drawable.ic_urgent)
                }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup) = ElementListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let(::ListViewHolder)
    }



    private fun <T : Any> views(block: ElementListBinding.() -> T): T = binding.block()

}