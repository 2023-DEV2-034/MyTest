package dev.test.game.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.test.game.core.BaseViewModel
import dev.test.game.presentation.contract.GameEvent
import dev.test.game.presentation.contract.GameSideEffect
import dev.test.game.presentation.contract.GameUiState
import dev.test.game.presentation.model.GamePlayer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() :
    BaseViewModel<GameUiState, GameEvent, GameSideEffect>(GameUiState.default) {

    override fun handleEvent(event: GameEvent) {
        when (event) {
            is GameEvent.PlayTurn -> turnPlayed(event)
        }
    }

    private fun turnPlayed(event: GameEvent.PlayTurn) {
        viewModelScope.launch {
            val playedCell = event.cell

            setState {
                // Do nothing if cell is already occupied
                val isCellPlayed = board.entries.any {
                    it.key == playedCell && it.value != GamePlayer.NONE
                }
                if (isCellPlayed) return@setState this

                // Update the state of the board
                val updatedBoard = board.toMutableMap().apply {
                    this[playedCell] = currentPlayer
                }

                // Switch player turns
                val nextPlayer = currentPlayer.nextPlayer()

                copy(
                    board = updatedBoard,
                    currentPlayer = nextPlayer
                )
            }
        }
    }
}
