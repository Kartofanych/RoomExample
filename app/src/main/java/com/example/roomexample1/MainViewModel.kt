package com.example.roomexample1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomexample1.repository.Repository
import com.example.roomexample1.room.TodoItem
import com.example.roomexample1.utils.localeLazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val repository: Repository by localeLazy()

    val data = repository.getList().asLiveDataFlow()

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


    fun getItem(id: String):SharedFlow<TodoItem>{
        return repository.getItem(id).asLiveDataFlow()
    }



    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}