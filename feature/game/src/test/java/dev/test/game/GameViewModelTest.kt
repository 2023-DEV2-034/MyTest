package dev.test.game

import dev.test.game.presentation.contract.GameEvent
import dev.test.game.presentation.model.GameCell
import dev.test.game.presentation.model.GamePlayer
import dev.test.game.presentation.model.GameStatus
import dev.test.game.presentation.viewmodel.GameViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assume
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var gameViewModel: GameViewModel

    @Before
    fun setUp() {
        gameViewModel = GameViewModel()
    }

    @Test
    fun `when screen starts expect empty board`() = runTest {
        val gameState = gameViewModel.uiState.first()
        val isBoardEmpty = gameState.board.all { it.value == GamePlayer.NONE }

        Assert.assertTrue(isBoardEmpty)
    }

    @Test
    fun `when screen starts expect player X to start`() = runTest {
        val gameState = gameViewModel.uiState.first()
        Assert.assertEquals(GamePlayer.X, gameState.currentPlayer)
    }

    @Test
    fun `when screen starts expect the game to be in progress`() = runTest {
        val gameState = gameViewModel.uiState.first()
        Assert.assertTrue(gameState.gameStatus is GameStatus.InProgress)
    }

    @Test
    fun `when a move is played on an empty cell expect it to succeed`() = runTest {
        // Assume there is at least one empty cell
        val startState = gameViewModel.uiState.first()
        val emptyCell = startState.board.entries.find { it.value == GamePlayer.NONE }?.key
        Assume.assumeNotNull(emptyCell)

        val currentPlayer = startState.currentPlayer
        gameViewModel.handleEvent(GameEvent.PlayTurn(emptyCell!!))
        val gameState = gameViewModel.uiState.first()
        val isCellPlayed = gameState.board.entries.find { it.key == emptyCell }
            ?.value == currentPlayer
        Assert.assertTrue(isCellPlayed)
    }

    @Test
    fun `when a move is played on an occupied cell expect it to be ignored`() = runTest {
        // Assume there is at least one empty cell
        val startState = gameViewModel.uiState.first()
        val gameCell = startState.board.entries.find { it.value == GamePlayer.NONE }?.key
        Assume.assumeNotNull(gameCell)

        // Assume the same cell was played
        gameViewModel.handleEvent(GameEvent.PlayTurn(gameCell!!))
        val assertionState = gameViewModel.uiState.first()
        val isCellPlayed = assertionState.board
            .entries
            .find { it.key == gameCell }
            ?.value == startState.currentPlayer
        Assume.assumeTrue(isCellPlayed)

        // Assert that nothing changed when the same cell was played again
        gameViewModel.handleEvent(GameEvent.PlayTurn(gameCell))
        Assert.assertEquals(assertionState, gameViewModel.uiState.first())
    }

    @Test
    fun `when player X plays expect player O to have the next turn`() = runTest {
        // Assume there is at least one empty cell and player X has the current turn
        val startState = gameViewModel.uiState.first()
        val gameCell = startState.board.entries.find { it.value == GamePlayer.NONE }?.key
        Assume.assumeNotNull(gameCell)
        Assume.assumeTrue(startState.currentPlayer == GamePlayer.X)

        // Player X makes his move
        gameViewModel.handleEvent(GameEvent.PlayTurn(gameCell!!))
        val gameState = gameViewModel.uiState.first()
        Assert.assertEquals(GamePlayer.O, gameState.currentPlayer)
    }

    @Test
    fun `when player O plays expect player X to have the next turn`() = runTest {
        // Assume player O plays the current turn
        val startState = gameViewModel.uiState.first()
        val firstCell = startState.board.entries.find { it.value == GamePlayer.NONE }?.key
        gameViewModel.handleEvent(GameEvent.PlayTurn(firstCell!!)) // X played
        Assume.assumeTrue(GamePlayer.O == gameViewModel.uiState.first().currentPlayer)

        // Player O makes his move
        val gameState = gameViewModel.uiState.first()
        val emptyCell = gameState.board.entries.find { it.value == GamePlayer.NONE }?.key
        gameViewModel.handleEvent(GameEvent.PlayTurn(emptyCell!!))
        Assert.assertEquals(GamePlayer.X, gameViewModel.uiState.first().currentPlayer)
    }

    @Test
    fun `when player X completes the top row expect X to win`() = runTest {
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C00))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C10))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C01))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C11))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C02))

        // Assert player X won
        val gameState = gameViewModel.uiState.first()
        Assert.assertTrue(gameState.gameStatus is GameStatus.Winner)
        val winningPlayer = (gameState.gameStatus as GameStatus.Winner).winningPlayer
        Assert.assertEquals(GamePlayer.X, winningPlayer)
    }

    @Test
    fun `when player X completes the left column expect X to win`() = runTest {
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C00))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C01))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C10))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C11))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C20))

        // Assert player X won
        val gameState = gameViewModel.uiState.first()
        Assert.assertTrue(gameState.gameStatus is GameStatus.Winner)
        val winningPlayer = (gameState.gameStatus as GameStatus.Winner).winningPlayer
        Assert.assertEquals(GamePlayer.X, winningPlayer)
    }

    @Test
    fun `when player X completes diagonally expect X to win`() = runTest {
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C00))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C01))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C11))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C10))
        gameViewModel.handleEvent(GameEvent.PlayTurn(GameCell.C22))

        // Assert player X won
        val gameState = gameViewModel.uiState.first()
        Assert.assertTrue(gameState.gameStatus is GameStatus.Winner)
        val winningPlayer = (gameState.gameStatus as GameStatus.Winner).winningPlayer
        Assert.assertEquals(GamePlayer.X, winningPlayer)
    }
}
