package dev.test.tictactoe.presentation.contract

import dev.test.tictactoe.presentation.model.GameCell
import dev.test.tictactoe.presentation.model.GamePlayer

/**
 * The UI state of the tic-tac-toe game screen.
 */
data class GameUiState(
    val board: Map<GameCell, GamePlayer> = GameCell.entries.associateWith { GamePlayer.NONE }
)
