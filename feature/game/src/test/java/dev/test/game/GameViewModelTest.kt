package dev.test.game

import dev.test.game.presentation.model.GamePlayer
import dev.test.game.presentation.viewmodel.GameViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

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
}
