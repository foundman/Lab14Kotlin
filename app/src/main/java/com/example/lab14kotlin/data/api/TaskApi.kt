package com.example.lab14kotlin.data.api
import com.example.lab14kotlin.data.model.Task
import retrofit2.http.GET

// Абстракция API. Конкретная реализация (Retrofit) вынесена наружу.
interface TaskApi {
    @GET("tasks")
    suspend fun fetchTasks(): List<Task>
}