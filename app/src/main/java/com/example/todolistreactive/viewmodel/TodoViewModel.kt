package com.example.todolistreactive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistreactive.model.Todo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    private val _filter = MutableStateFlow("all")
    val filter: StateFlow<String> = _filter

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    //Flow yang selalu berubah mengikuti todos, filter, dan pencarian
    val filteredTodos: StateFlow<List<Todo>> = combine(_todos, _filter, _searchQuery) { todos, filter, query ->
        var result = when (filter) {
            "active" -> todos.filter { !it.isDone }
            "done" -> todos.filter { it.isDone }
            else -> todos
        }
        if (query.isNotBlank()) {
            result = result.filter { it.title.contains(query, ignoreCase = true) }
        }
        result
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setFilter(value: String) {
        _filter.value = value
    }

    fun setSearchQuery(value: String) {
        _searchQuery.value = value
    }

    fun addTask(title: String) {
        val nextId = (_todos.value.maxOfOrNull { it.id } ?: 0) + 1
        _todos.value = _todos.value + Todo(nextId, title)
    }

    fun toggleTask(id: Int) {
        _todos.value = _todos.value.map {
            if (it.id == id) it.copy(isDone = !it.isDone) else it
        }
    }

    fun deleteTask(id: Int) {
        _todos.value = _todos.value.filterNot { it.id == id }
    }

    fun getCounts(): Triple<Int, Int, Int> {
        val all = _todos.value.size
        val active = _todos.value.count { !it.isDone }
        val done = _todos.value.count { it.isDone }
        return Triple(all, active, done)
    }
}
