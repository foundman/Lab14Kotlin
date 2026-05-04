package com.example.lab14kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lab14kotlin.data.repository.TaskRepository
import com.example.lab14kotlin.data.source.LocalTaskDataSource
import com.example.lab14kotlin.ui.TaskListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // DIP: Конкретные реализации создаются в точках сборки (Activity/Compose),
        // а не внутри бизнес-логики или UI.
        val localSource = LocalTaskDataSource()
        val repository = TaskRepository(
            listProvider = localSource, // В реальном проекте здесь был бы RemoteTaskDataSource
            saver = localSource
        )

        setContent {
            TaskListScreen(repository = repository)
        }
    }
}