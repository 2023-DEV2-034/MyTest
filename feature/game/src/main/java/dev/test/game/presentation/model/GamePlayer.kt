package dev.test.game.presentation.model

/**
 * Enum class to represent the players in a Tic-Tac-Toe game
 * including a NONE state for unoccupied cells.
 */
enum class GamePlayer {
    X,
    O,
    NONE;

    /**
     * Returns the next player to switch turns.
     */
    fun nextPlayer(): GamePlayer = if (this == X) O else X
}
