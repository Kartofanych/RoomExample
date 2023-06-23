package com.example.roomexample1.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todolist")
data class TodoItem(
    @PrimaryKey var id:String,
    var text:String,
    var importance: Importance,
    var deadline : Long?,
    var done : Boolean,
    var dateCreation : Long,
    var dateChanged : String?
)


enum class Importance{
    LOW,
    MIDDLE,
    HIGH
}
