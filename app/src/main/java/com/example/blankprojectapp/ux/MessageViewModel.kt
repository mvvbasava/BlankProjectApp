package com.example.blankprojectapp.ux

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankprojectapp.data.MessageApiJsonData
import com.example.blankprojectapp.repository.MessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class UiState {
    object Loading : UiState()
    data class Success(val data: MessageApiJsonData) : UiState()
    data class Error(val errorMessage: String) : UiState()
}

class MessageViewModel : ViewModel() {
    private val repository = MessageRepository()
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchMessageData()
    }

    fun fetchMessageData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.fetchMessages()
                if (response?.isSuccessful == true) {
                    val data = response.body()
                    if (data != null) {
                        _uiState.value = UiState.Success(data)
                    } else {
                        _uiState.value = UiState.Error("message data is null")
                    }
                } else {
                    _uiState.value = UiState.Error("Error: ${response?.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Exception: ${e.message}")
            }
        }
    }
}
