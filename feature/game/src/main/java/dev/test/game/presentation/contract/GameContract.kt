package dev.test.game.presentation.contract

import dev.test.game.presentation.model.GameCell
import dev.test.game.presentation.model.GamePlayer
import dev.test.game.presentation.model.GameStatus

/**
 * The UI state of the tic-tac-toe game screen.
 */
data class GameUiState(
    val board: Map<GameCell, GamePlayer>,
    val currentPlayer: GamePlayer,
    val gameStatus: GameStatus,
) {

    companion object {
        val default = GameUiState(
            board = GameCell.entries.associateWith { GamePlayer.NONE },
            currentPlayer = GamePlayer.X,
            gameStatus = GameStatus.IN_PROGRESS,
        )
    }
}
