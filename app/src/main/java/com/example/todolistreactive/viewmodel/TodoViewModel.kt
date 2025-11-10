package com.example.todolistreactive.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.todolistreactive.model.Todo

class TodoViewModel: ViewModel() {

    // 1. STATE: State yang bisa diubah (Hanya bisa diakses di ViewModel)
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())

    // 2. STATE: State yang hanya bisa dibaca (digunakan oleh UI untuk mengamati perubahan)
    val todos: StateFlow<List<Todo>> = _todos

    // 3. EVENT/ACTION: Menambahkan tugas baru
    fun addTask(title: String) {
        // Hitung ID baru: Cari ID tertinggi saat ini, lalu tambahkan 1.
        val nextId = (_todos.value.maxOfOrNull { it.id } ?: 0) + 1

        // Buat objek Todo baru
        val newTask = Todo(id = nextId, title = title)

        // Perbarui State: Ambil list lama, tambahkan tugas baru, lalu set ke _todos.value
        _todos.value = _todos.value + newTask
    }

    // 4. EVENT/ACTION: Mengubah status selesai/belum selesai (toggle)
    fun toggleTask(id: Int) {
        // Perbarui State: Lakukan mapping pada list lama
        _todos.value = _todos.value.map { t ->
            // Jika ID cocok, buat salinan (copy) dengan status isDone yang dibalik
            if (t.id == id) t.copy(isDone = !t.isDone) else t
        }
    }

    // 5. EVENT/ACTION: Menghapus tugas
    fun deleteTask(id: Int) {
        // Perbarui State: Gunakan filterNot untuk menghapus tugas dengan ID yang cocok
        _todos.value = _todos.value.filterNot { it.id == id }
    }
}