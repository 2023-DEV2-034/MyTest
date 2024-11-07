package dev.test.tictactoe.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.test.design.theme.TicTacToeTheme
import dev.test.tictactoe.presentation.contract.GameUiState
import dev.test.tictactoe.presentation.model.GameCell
import dev.test.tictactoe.presentation.model.GamePlayer
import dev.test.tictactoe.presentation.viewmodel.GameViewModel

@Immutable
data object GameScreen {

    @Composable
    operator fun invoke(
        viewModel: GameViewModel
    ) {
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        content(uiState.value)
    }

    @Composable
    internal fun content(
        uiState: GameUiState
    ) {
        TicTacToeTheme {
            Surface(
                color = TicTacToeTheme.colors.surfacePrimary
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val colors = TicTacToeTheme.colors

                    BasicText(
                        modifier = Modifier.align(Alignment.TopCenter),
                        text = "TIC TAC TOE",
                        style = TicTacToeTheme.typography.H1,
                        color = { colors.textPrimary }
                    )

                    Board(
                        items = uiState.board,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .align(Alignment.Center),
                        itemContent = { _, _ ->
                            // Cell item
                            Spacer(
                                modifier = Modifier
                                    .background(TicTacToeTheme.colors.surfaceSecondary)
                            )
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun Board(
        items: Map<GameCell, GamePlayer>,
        modifier: Modifier = Modifier,
        spacing: Dp = 6.dp,
        itemContent: @Composable (GameCell, GamePlayer) -> Unit
    ) {
        require(items.size == GameCell.entries.size) { "The board must be a 3x3 grid." }

        // Implement a custom layout as the final design is already known
        Layout(
            content = {
                items.forEach { item -> itemContent(item.key, item.value) }
            },
            modifier = modifier
        ) { measurables, constraints ->
            // Adjust cell size based on spacing
            val spacingPx = spacing.roundToPx()
            val cellSize = (constraints.maxWidth - spacingPx * 2) / 3

            // Force squared cells
            val cellConstraints = constraints.copy(
                minWidth = cellSize,
                maxWidth = cellSize,
                minHeight = cellSize,
                maxHeight = cellSize
            )

            val placeables = measurables.map { measurable ->
                measurable.measure(cellConstraints)
            }

            // Place the items
            layout(constraints.maxWidth, constraints.maxWidth) {
                placeables.forEachIndexed { index, placeable ->
                    val row = index / 3
                    val col = index % 3
                    placeable.placeRelative(
                        x = col * (cellSize + spacingPx),
                        y = row * (cellSize + spacingPx)
                    )
                }
            }
        }
    }
}

@Preview(name = "Game screen at start")
@Composable
private fun GameScreenStartPreview() {
    GameScreen.content(GameUiState())
}
