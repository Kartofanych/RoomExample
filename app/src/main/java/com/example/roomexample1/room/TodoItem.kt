package com.example.roomexample1.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "todolist")
data class TodoItem(
    @PrimaryKey var id:String,
    var text:String,
    var importance: Importance,
    var deadline : Long?,
    var done : Boolean,
    var dateCreation : Long,
    var dateChanged : String?
){
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}


enum class Importance{
    LOW,
    MIDDLE,
    HIGH
}
