package com.example.lab14kotlin.data.repository
import com.example.lab14kotlin.data.model.Task
import com.example.lab14kotlin.data.source.TaskListProvider
import com.example.lab14kotlin.data.source.TaskSaver

// SRP: Координирует источники данных. Не содержит UI-логики и сетевых вызовов.
// OCP: Открыт для расширения (можно добавить новый источник через конструктор), закрыт для модификации.
class TaskRepository(
    private val listProvider: TaskListProvider,
    private val saver: TaskSaver
) {
    suspend fun loadTasks(): List<Task> = listProvider.getTasks()
    suspend fun addTask(task: Task) = saver.saveTask(task)
}