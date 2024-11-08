package dev.test.game.presentation.contract

import dev.test.game.core.UiEvent
import dev.test.game.core.UiSideEffect
import dev.test.game.core.UiState
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
): UiState {

    companion object {
        val default = GameUiState(
            board = GameCell.entries.associateWith { GamePlayer.NONE },
            currentPlayer = GamePlayer.X,
            gameStatus = GameStatus.IN_PROGRESS,
        )
    }
}

/**
 * The class represents all of the UI events that can be triggered in the tic-tac-toe game screen.
 */
sealed class GameEvent : UiEvent {
    data class PlayTurn(val cell: GameCell) : GameEvent()
}

/**
 * The class represents all of the UI side effects that can be triggered
 * in the tic-tac-toe game screen.
 */
sealed class GameSideEffect : UiSideEffect
