package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

/*
 *
 *  EXPLICAÇÃO DAS CORROTINAS
 *
 *
 * Como isso funciona?
 *
 * `viewModelScope.launch` (o "garçom") inicia o trabalho fora da tela principal
 * (a UI não trava).
 *
 * TAREFA 1: SEQUENCIAL (Lento)
 * É como pedir um prato, o cozinheiro prepara (delay 2s), entrega,
 * e SÓ DEPOIS começa a fazer o segundo prato (delay 3s).
 * O tempo total é a SOMA: 2s + 3s = 5 segundos.
 *
 * TAREFA 2: PARALELO (Rápido) - AQUI ESTÁ A MELHORIA!
 * Usamos `async`. Isso é como pedir DOIS pratos de uma vez.
 * - `async { fetchUserData() }` -> Cozinheiro 1 começa o prato de 2s
 * - `async { fetchProfileData() }` -> Cozinheiro 2 começa o prato de 3s
 * Os dois cozinheiros trabalham AO MESMO TEMPO.
 *
 * O `await()` no final é o "garçom" esperando que OS DOIS pratos fiquem prontos.
 * O tempo total é o tempo do prato MAIS DEMORADO: 3 segundos.
 *
 * Isso é muito mais rápido e eficiente!
 */
class MainViewModel : ViewModel() {

    private val _statusText = MutableStateFlow("Clique em um botão para iniciar uma tarefa.")
    val statusText = _statusText.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun fetchSequentially() {
        viewModelScope.launch {
            _isLoading.value = true
            _statusText.value = "Iniciando tarefas em sequência..."

            val tempoTotal = measureTimeMillis {

                val userData = fetchUserData()
                _statusText.value = "Usuário buscado... buscando perfil..."

                val profileData = fetchProfileData()


                _statusText.value = "SEQUENCIAL:\n$userData\n$profileData"
            }
            _statusText.value = "${_statusText.value}\nTempo total: ${tempoTotal}ms (Lento!)"
            _isLoading.value = false
        }
    }


    fun fetchInParallel() {
        viewModelScope.launch {
            _isLoading.value = true
            _statusText.value = "Iniciando tarefas em PARALELO..."

            val tempoTotal = measureTimeMillis {

                val userDataJob = async { fetchUserData() }
                val profileDataJob = async { fetchProfileData() }


                val userData = userDataJob.await()
                val profileData = profileDataJob.await()


                _statusText.value = "PARALELO:\n$userData\n$profileData"
            }
            _statusText.value = "${_statusText.value}\nTempo total: ${tempoTotal}ms (Rápido!)"
            _isLoading.value = false
        }
    }




    private suspend fun fetchUserData(): String {
        withContext(Dispatchers.IO) {
            delay(2000)
        }
        return "Dados do Usuário (de 2s)"
    }


    private suspend fun fetchProfileData(): String {
        withContext(Dispatchers.IO) {
            delay(3000)
        }
        return "Dados do Perfil (de 3s)"
    }
}