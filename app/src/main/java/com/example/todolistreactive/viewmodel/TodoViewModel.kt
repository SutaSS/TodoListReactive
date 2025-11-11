package com.example.todolistreactive.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.todolistreactive.model.Todo

class TodoViewModel : ViewModel() {

    // State utama: daftar semua Todo
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    // Filter: "all", "active", "done"
    private val _filter = MutableStateFlow("all")
    val filter: StateFlow<String> = _filter

    // Pencarian real-time
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun setFilter(value: String) {
        _filter.value = value
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // List hasil akhir setelah filter + pencarian
    fun getFilteredTodos(): List<Todo> {
        val filtered = when (_filter.value) {
            "active" -> _todos.value.filter { !it.isDone }
            "done" -> _todos.value.filter { it.isDone }
            else -> _todos.value
        }
        return filtered.filter { it.title.contains(_searchQuery.value, ignoreCase = true) }
    }

    // Tambahkan tugas baru
    fun addTask(title: String) {
        val nextId = (_todos.value.maxOfOrNull { it.id } ?: 0) + 1
        val newTask = Todo(id = nextId, title = title)
        _todos.value = _todos.value + newTask
    }

    // Toggle status selesai/aktif
    fun toggleTask(id: Int) {
        _todos.value = _todos.value.map { t ->
            if (t.id == id) t.copy(isDone = !t.isDone) else t
        }
    }

    // Hapus tugas berdasarkan ID
    fun deleteTask(id: Int) {
        _todos.value = _todos.value.filterNot { it.id == id }
    }

    // Hitung jumlah semua, aktif, dan selesai
    fun getCounts(): Triple<Int, Int, Int> {
        val all = _todos.value.size
        val active = _todos.value.count { !it.isDone }
        val done = _todos.value.count { it.isDone }
        return Triple(all, active, done)
    }
}
