package dev.test.tictactoe.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dev.test.game.presentation.screen.GameScreen
import dev.test.game.presentation.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent { GameScreen(viewModel) }
    }
}
