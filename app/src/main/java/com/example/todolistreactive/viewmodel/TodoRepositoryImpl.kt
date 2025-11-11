package com.example.todolistreactive.viewmodel

import com.example.todolistreactive.model.Todo

class TodoRepositoryImpl : TodoRepository {

    // Dummy in-memory list sebagai penyimpanan sementara
    private val data = mutableListOf<Todo>()

    override suspend fun loadAll(): List<Todo> = data

    override suspend fun insert(title: String) {
        val nextId = (data.maxOfOrNull { it.id } ?: 0) + 1
        data.add(Todo(id = nextId, title = title))
    }

    override suspend fun delete(id: Int) {
        data.removeAll { it.id == id }
    }

    override suspend fun toggle(id: Int) {
        val index = data.indexOfFirst { it.id == id }
        if (index != -1) {
            val old = data[index]
            data[index] = old.copy(isDone = !old.isDone)
        }
    }
}