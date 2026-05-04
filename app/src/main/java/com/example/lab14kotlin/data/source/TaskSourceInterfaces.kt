package com.example.lab14kotlin.data.source
import com.example.lab14kotlin.data.model.Task

// ISP: Узкие интерфейсы. Клиенты зависят только от нужных методов.
interface TaskListProvider { suspend fun getTasks(): List<Task> }
interface TaskSaver { suspend fun saveTask(task: Task) }