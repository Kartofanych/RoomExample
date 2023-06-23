package com.example.roomexample1

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomexample1.repository.Repository
import com.example.roomexample1.room.TodoItem
import com.example.roomexample1.utils.localeLazy
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository: Repository by localeLazy()
    var modeAll: Boolean = false

    val data = MutableSharedFlow<List<TodoItem>>()

    val item = MutableSharedFlow<TodoItem>()



    var job: Job? = null
    fun changeMode() {
        job?.cancel()
        modeAll = !modeAll
        getData()
    }
    fun getData(){
        when(modeAll) {
            true-> loadAllData()
            false-> loadToDoData()
        }
    }

    private fun loadToDoData() {
        job = viewModelScope.launch {
            data.emitAll(repository.getTodoList().asLiveDataFlow())

        }
    }
    private fun loadAllData() {
        job = viewModelScope.launch {
            data.emitAll(repository.getList().asLiveDataFlow())
        }
    }

    fun addItem(todoItem: TodoItem) {
        viewModelScope.launch {
            repository.addItem(todoItem)
        }
    }

    fun deleteItem(todoItem: TodoItem) {
        viewModelScope.launch {
            repository.deleteItem(todoItem)
        }
    }

    fun changeItem(newItem: TodoItem) {
        viewModelScope.launch {
            repository.changeItem(newItem)
        }
    }

    fun changeItemDone(id: String, done: Boolean) {
        viewModelScope.launch {
            repository.changeDone(id, done)
        }
    }


    fun loadItem(id: String) {
        viewModelScope.launch {
            item.emitAll(repository.getItem(id).asLiveDataFlow())
        }
    }


    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}