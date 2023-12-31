package com.example.roomexample1

import android.app.Application
import android.content.Context
import com.example.roomexample1.repository.Repository
import com.example.roomexample1.room.TodoListDatabase
import com.example.roomexample1.utils.ServiceLocator
import com.example.roomexample1.utils.locale

class App:Application() {


    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this)
        ServiceLocator.register(TodoListDatabase.create(locale()))
        ServiceLocator.register(Repository(locale()))
    }

}