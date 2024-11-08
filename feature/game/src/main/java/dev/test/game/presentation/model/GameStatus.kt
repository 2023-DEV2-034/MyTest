package dev.test.game.presentation.model

/**
 * Enum class to represent the current status of the tic-tac-toe game.
 */
sealed class GameStatus {
    data object InProgress : GameStatus()
    data class Winner(val winningPlayer: GamePlayer) : GameStatus()
}
