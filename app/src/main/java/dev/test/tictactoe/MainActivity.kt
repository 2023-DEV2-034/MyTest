package dev.test.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dev.test.tictactoe.presentation.screen.GameScreen
import dev.test.tictactoe.presentation.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent { GameScreen(viewModel) }
    }
}
