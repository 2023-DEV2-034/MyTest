package dev.test.game.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.test.design.extension.toRoundShape
import dev.test.design.theme.TicTacToeTheme
import dev.test.game.presentation.contract.GameUiState
import dev.test.game.presentation.model.GameCell
import dev.test.game.presentation.model.GamePlayer
import dev.test.game.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Content(uiState.value)
}

@Composable
private fun Content(
    uiState: GameUiState = GameUiState.default
) {
    TicTacToeTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            containerColor = TicTacToeTheme.colors.surfacePrimary,
            topBar = { GameHeader() },
            content = { contentPadding ->
                GameContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    uiState = uiState
                )
            }
        )
    }
}

@Composable
private fun GameHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        val colors = TicTacToeTheme.colors

        // Title
        BasicText(
            modifier = Modifier.align(Alignment.TopCenter),
            text = "TIC TAC TOE",
            style = TicTacToeTheme.typography.H1,
            color = { colors.textPrimary }
        )
    }
}

@Composable
private fun GameContent(
    uiState: GameUiState,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        TicTacToeBoard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center),
            items = uiState.board,
            spacing = TicTacToeTheme.dimensions.spacing.SpaceSmall,
            itemContent = { _, _ -> BoardCell() }
        )
    }
}

@Composable
private fun TicTacToeBoard(
    items: Map<GameCell, GamePlayer>,
    modifier: Modifier = Modifier,
    spacing: Dp = Dp.Unspecified,
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
        val spacingPx = spacing.takeIf { it.isSpecified }?.roundToPx() ?: 0
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

@Composable
private fun BoardCell(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier.background(
            color = TicTacToeTheme.colors.surfaceSecondary,
            shape = TicTacToeTheme.dimensions.cornerRadius.R8.toRoundShape
        )
    )
}

@Preview(name = "Game screen at start")
@Composable
private fun GameScreenStartPreview() {
    Content()
}
