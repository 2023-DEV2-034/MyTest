package dev.test.game.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.test.game.presentation.contract.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState.default)
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
}
