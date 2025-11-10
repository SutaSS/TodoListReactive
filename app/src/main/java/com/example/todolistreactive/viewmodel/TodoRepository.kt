package com.example.todolistreactive.viewmodel

import com.example.todolistreactive.model.Todo

interface TodoRepository {
    suspend fun loadAll(): List<Todo>
    suspend fun insert(title: String)
    suspend fun delete(id: Int)
    suspend fun toggle(id: Int)
}