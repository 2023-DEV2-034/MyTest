package dev.test.tictactoe

import dev.test.tictactoe.presentation.viewmodel.GameViewModel
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var gameViewModel: GameViewModel

    @Before
    fun setUp() {
        gameViewModel = GameViewModel()
    }

    @Test
    fun `when screen starts expect empty board`() {
    }
}
