package com.example.roomexample1.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todolist")
data class TodoItem(
    @PrimaryKey val id:String,
    var text:String,
    var importance: Importance,
)


enum class Importance{
    LOW,
    MIDDLE,
    HIGH
}
