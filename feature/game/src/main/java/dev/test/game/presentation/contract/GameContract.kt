package dev.test.game.presentation.contract

import dev.test.game.presentation.model.GameCell
import dev.test.game.presentation.model.GamePlayer

/**
 * The UI state of the tic-tac-toe game screen.
 */
data class GameUiState(
    val board: Map<GameCell, GamePlayer>
) {

    companion object {
        val default = GameUiState(
            board = GameCell.entries.associateWith { GamePlayer.NONE }
        )
    }
}
