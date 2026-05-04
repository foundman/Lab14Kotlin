package com.example.lab14kotlin.ui
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab14kotlin.data.model.Task
import com.example.lab14kotlin.data.model.TaskType
import kotlinx.coroutines.launch

// ISP: Экран зависит от узкого контракта репозитория.
// LSP: Любой подтип TaskListProvider/TaskSaver может быть передан без изменения поведения экрана.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(repository: com.example.lab14kotlin.data.repository.TaskRepository) {
    var tasks by remember { mutableStateOf<List<Task>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        tasks = repository.loadTasks()
        isLoading = false
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tasks (SOLID Demo)") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    repository.addTask(Task(
                        id = System.currentTimeMillis(),
                        title = "Новая задача",
                        description = "Добавлена через DIP",
                        type = TaskType.NORMAL
                    ))
                    tasks = repository.loadTasks()
                }
            }) { Icon(androidx.compose.material.icons.Icons.Default.Add, "Add") }
        }
    ) { padding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks) { task -> TaskItem(task = task) }
            }
        }
    }
}

// OCP: Рендеринг зависит от типа задачи. Добавление нового типа не требует изменения TaskListScreen.
@Composable
fun TaskItem(task: Task) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
        containerColor = when(task.type) {
            TaskType.URGENT -> MaterialTheme.colorScheme.errorContainer
            TaskType.REMINDER -> MaterialTheme.colorScheme.tertiaryContainer
            else -> MaterialTheme.colorScheme.surfaceVariant
        }
    )) {
        Column(Modifier.padding(12.dp)) {
            Text(task.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(task.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}