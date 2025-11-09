package com.example.todolistreactive.model

data class Todo (
val id: Int, // ID unik untuk setiap tugas [cite: 42]
val title: String, // Judul tugas [cite: 43]
val isDone: Boolean = false // Status selesai, default-nya false (belum selesai) [cite: 44]
)