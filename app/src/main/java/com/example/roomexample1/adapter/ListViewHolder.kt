package com.example.roomexample1.adapter

import android.graphics.Paint
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample1.R
import com.example.roomexample1.databinding.ElementListBinding
import com.example.roomexample1.room.Importance
import com.example.roomexample1.room.TodoItem
import java.util.Date

class ListViewHolder(private val binding: ElementListBinding): RecyclerView.ViewHolder(binding.root){

    private var todoItem: TodoItem? = null

    fun onBind(todoItem: TodoItem, callbacks: AdapterClickCallbacks){
        this.todoItem = todoItem
        views {
            if(todoItem.done) {
                text.text = todoItem.text
                text.paintFlags = text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                text.setTextColor(itemView.context.getColor(R.color.tertiary))

                checkBox.isChecked = true
                checkBox.buttonTintList = AppCompatResources.getColorStateList(itemView.context,
                    R.color.green
                )

                importance.visibility = View.GONE
                deadline.visibility = View.GONE



            }else{
                text.text = todoItem.text
                text.paintFlags = text.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                text.setTextColor(itemView.context.getColor(R.color.primary))
                checkBox.isChecked = false
                if(todoItem.deadline != null) {
                    deadline.visibility = View.VISIBLE
                    deadline.text = DateFormat.format("hh:mm:ss, MMM dd, yyyy", Date(todoItem.deadline!!)).toString()
                }else{
                    deadline.visibility = View.GONE
                }
                when (todoItem.importance) {
                    Importance.HIGH -> {
                        importance.visibility = View.VISIBLE
                        checkBox.buttonTintList =
                            AppCompatResources.getColorStateList(itemView.context, R.color.red)
                        importance.setImageDrawable(
                            AppCompatResources.getDrawable(itemView.context,
                            R.drawable.ic_urgent
                        ))
                    }
                    Importance.LOW -> {
                        importance.visibility = View.VISIBLE
                        checkBox.buttonTintList =
                            AppCompatResources.getColorStateList(itemView.context,
                                R.color.separator
                            )
                        importance.setImageDrawable(
                            AppCompatResources.getDrawable(itemView.context,
                            R.drawable.ic_low
                        ))
                    }
                    Importance.MIDDLE -> {
                        importance.visibility = View.GONE
                        checkBox.buttonTintList =
                            AppCompatResources.getColorStateList(itemView.context,
                                R.color.separator
                            )
                    }
                }
            }

            checkBox.setOnClickListener {
                //todoItem.done = binding.checkBox.isChecked
                callbacks.onCheckClick(todoItem.id, checkBox.isChecked)
                //notifyItemChanged(absoluteAdapterPosition)
            }


            itemView.setOnClickListener {
                callbacks.onItemClick(todoItem.id)

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