package dev.test.game.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.test.game.core.BaseViewModel
import dev.test.game.presentation.contract.GameEvent
import dev.test.game.presentation.contract.GameSideEffect
import dev.test.game.presentation.contract.GameUiState
import dev.test.game.presentation.model.GameCell
import dev.test.game.presentation.model.GamePlayer
import dev.test.game.presentation.model.GameStatus
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() :
    BaseViewModel<GameUiState, GameEvent, GameSideEffect>(GameUiState.default) {

    // Predefined winning patterns as bitmasks for a 3x3 board
    private val winningPatterns = listOf(
        0b111000000, // Top row
        0b000111000, // Middle row
        0b000000111, // Bottom row
        0b100100100, // Left column
        0b010010010, // Middle column
        0b001001001, // Right column
        0b100010001, // Main diagonal
        0b001010100  // Anti-diagonal
    )

    private val cellBitPositions = mapOf(
        GameCell.C00 to 0,
        GameCell.C01 to 1,
        GameCell.C02 to 2,
        GameCell.C10 to 3,
        GameCell.C11 to 4,
        GameCell.C12 to 5,
        GameCell.C20 to 6,
        GameCell.C21 to 7,
        GameCell.C22 to 8
    )


    override fun handleEvent(event: GameEvent) {
        when (event) {
            is GameEvent.PlayTurn -> turnPlayed(event)
        }
    }

    private fun turnPlayed(event: GameEvent.PlayTurn) {
        viewModelScope.launch {
            val playedCell = event.cell

            setState {
                // Do nothing if the game is finished or if the cell was already played
                val isCellPlayed = board[playedCell] != GamePlayer.NONE
                if (gameStatus != GameStatus.InProgress || isCellPlayed) return@setState this

                // Update the state of the board
                val updatedBoard = board.toMutableMap().apply {
                    this[playedCell] = currentPlayer
                }

                val gameStatus = checkGameStatus(updatedBoard)

                // Switch player turns if there is no winner
                val nextPlayer = if (gameStatus == GameStatus.InProgress) currentPlayer.nextPlayer()
                else currentPlayer

                copy(
                    board = updatedBoard,
                    currentPlayer = nextPlayer,
                    gameStatus = gameStatus
                )
            }
        }
    }

    private fun checkGameStatus(board: Map<GameCell, GamePlayer>): GameStatus {
        // Generate bitmasks for Player X and Player O based on the current board state
        val playerXBitmask = generateBitmask(board, GamePlayer.X)
        val playerOBitmask = generateBitmask(board, GamePlayer.O)

        // Check if Player X has a winning pattern
        if (winningPatterns.any { pattern -> (playerXBitmask and pattern) == pattern }) {
            return GameStatus.Winner(GamePlayer.X)
        }

        // Check if Player O has a winning pattern
        if (winningPatterns.any { pattern -> (playerOBitmask and pattern) == pattern }) {
            return GameStatus.Winner(GamePlayer.O)
        }

        return GameStatus.InProgress
    }

    private fun generateBitmask(
        board: Map<GameCell, GamePlayer>,
        player: GamePlayer
    ): Int = board.entries.fold(0) { bitmask, (cell, cellPlayer) ->
        if (cellPlayer == player) {
            bitmask or (1 shl cellBitPositions[cell]!!)
        } else {
            bitmask
        }
    }
}
