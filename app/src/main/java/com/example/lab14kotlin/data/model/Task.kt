package com.example.lab14kotlin.data.model

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val type: TaskType
)

enum class TaskType { NORMAL, URGENT, REMINDER }