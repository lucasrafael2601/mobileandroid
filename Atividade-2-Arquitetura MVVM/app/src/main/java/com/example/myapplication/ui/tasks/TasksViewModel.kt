package com.example.myapplication.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.state.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class Task(
    val id: Int,
    val title: String,
    val done: Boolean
)

class TasksViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Task>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Task>>> = _uiState

    init {
        loadTasks()
    }


    fun loadTasks() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {

            delay(2000)

            val success = Random.nextBoolean()
            if (success) {
                val items = listOf(
                    Task(1, "Estudar ViewModel e StateFlow", done = false),
                    Task(2, "Implementar UiState", done = true),
                    Task(3, "Construir a tela com Compose", done = false),
                    Task(4, "Testar o fluxo (recarregar/tentar novamente)", done = false)
                )
                _uiState.value = UiState.Success(items)
            } else {
                _uiState.value = UiState.Error("Falha ao carregar tarefas. Verifique sua conexão e tente novamente.")
            }
        }
    }


    fun toggleTaskStatus(taskId: Int) {

        val currentState = _uiState.value


        if (currentState is UiState.Success) {


            val currentTasks = currentState.data


            val updatedTasks = currentTasks.map { task ->
                if (task.id == taskId) {

                    task.copy(done = !task.done)
                } else {

                    task
                }
            }


            _uiState.value = UiState.Success(updatedTasks)
        }
    }
}