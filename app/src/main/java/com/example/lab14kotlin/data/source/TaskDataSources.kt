package com.example.lab14kotlin.data.source
import com.example.lab14kotlin.data.model.Task
import com.example.lab14kotlin.data.api.TaskApi

// SRP: Отвечает ТОЛЬКО за сетевую загрузку. Не занимается UI или кэшированием.
// DIP: Зависит от абстракции TaskApi, а не от класса Retrofit.
class RemoteTaskDataSource(private val api: TaskApi) : TaskListProvider {
    override suspend fun getTasks(): List<Task> = api.fetchTasks()
}

// SRP: Отвечает ТОЛЬКО за локальное сохранение (для демонстрации используем in-memory список)
class LocalTaskDataSource : TaskListProvider, TaskSaver {
    private val tasks = mutableListOf<Task>()
    // Реализация TaskListProvider
    override suspend fun getTasks(): List<Task> = tasks.toList()

    // Реализация TaskSaver
    override suspend fun saveTask(task: Task) {
        tasks.add(task)
    }
    fun getLocalTasks(): List<Task> = tasks.toList()
}