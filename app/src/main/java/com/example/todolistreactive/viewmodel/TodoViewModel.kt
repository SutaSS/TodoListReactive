package com.example.todolistreactive.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.todolistreactive.model.Todo

class   TodoViewModel: ViewModel() {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos
    fun addTask(title: String) {
        // Hitung ID baru: Cari ID tertinggi saat ini, lalu tambahkan 1.
        val nextId = (_todos.value.maxOfOrNull { it.id } ?: 0) + 1

        // Buat objek Todo baru
        val newTask = Todo(id = nextId, title = title)

        // Perbarui State: Ambil list lama, tambahkan tugas baru, lalu set ke _todos.value
        _todos.value = _todos.value + newTask
    }
    fun toggleTask(id: Int) {
        // Perbarui State: Lakukan mapping pada list lama
        _todos.value = _todos.value.map { t ->
            // Jika ID cocok, buat salinan (copy) dengan status isDone yang dibalik
            if (t.id == id) t.copy(isDone = !t.isDone) else t
        }
    }
    fun deleteTask(id: Int) {
        // Perbarui State: Gunakan filterNot untuk menghapus tugas dengan ID yang cocok
        _todos.value = _todos.value.filterNot { it.id == id }
    }
}