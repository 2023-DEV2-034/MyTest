package dev.test.game.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.coroutineScope
import dev.test.design.extension.toRoundShape
import dev.test.design.theme.TicTacToeTheme
import dev.test.game.presentation.contract.GameEvent
import dev.test.game.presentation.contract.GameUiState
import dev.test.game.presentation.model.GameCell
import dev.test.game.presentation.model.GamePlayer
import dev.test.game.presentation.model.GameStatus
import dev.test.game.presentation.viewmodel.GameViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val eventsChannel = viewModel.attachChannel()

    Content(
        uiState = uiState.value,
        eventsChannel = eventsChannel
    )
}

@Composable
private fun Content(
    uiState: GameUiState = GameUiState.default,
    eventsChannel: Channel<GameEvent> = Channel()
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
                    uiState = uiState,
                    eventsChannel = eventsChannel
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
    eventsChannel: Channel<GameEvent>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = TicTacToeTheme.dimensions.spacing.SpaceLarge,
            alignment = Alignment.CenterVertically
        )
    ) {
        val colors = TicTacToeTheme.colors

        // Game status
        val statusText = when (uiState.gameStatus) {
            GameStatus.InProgress -> "Player turn : ${uiState.currentPlayer.name}"
            is GameStatus.Winner -> "PLAYER ${uiState.currentPlayer.name} WINS!"
        }

        BasicText(
            text = statusText,
            style = TicTacToeTheme.typography.H4,
            color = {
                when (uiState.currentPlayer) {
                    GamePlayer.X -> colors.iconPrimary
                    GamePlayer.O -> colors.iconSecondary
                    GamePlayer.NONE -> colors.textPrimary
                }
            }
        )

        TicTacToeBoard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .graphicsLayer {
                    alpha = if (uiState.gameStatus is GameStatus.InProgress) 1.0f else .2f
                },
            items = uiState.board,
            spacing = TicTacToeTheme.dimensions.spacing.SpaceSmall,
            itemContent = { cell, player ->
                BoardCell(
                    modifier = Modifier.clickable(uiState.gameStatus is GameStatus.InProgress) {
                        val event = GameEvent.PlayTurn(cell)
                        eventsChannel.trySend(event)
                    },
                    player = player
                )
            }
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
    player: GamePlayer,
    modifier: Modifier = Modifier,
) {
    // Use colors to differentiate players
    val color = when (player) {
        GamePlayer.X -> TicTacToeTheme.colors.iconPrimary
        GamePlayer.O -> TicTacToeTheme.colors.iconSecondary
        GamePlayer.NONE -> TicTacToeTheme.colors.surfaceSecondary
    }

    Spacer(
        modifier = modifier.background(
            color = color,
            shape = TicTacToeTheme.dimensions.cornerRadius.R8.toRoundShape
        )
    )
}

@Composable
private fun GameViewModel.attachChannel(): Channel<GameEvent> {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val eventsChannel = remember { Channel<GameEvent>() }

    LaunchedEffect(key1 = lifecycle) {
        eventsChannel
            .consumeAsFlow()
            .onEach { event -> handleEvent(event) }
            .launchIn(lifecycle.coroutineScope)
    }

    return eventsChannel
}

@Preview(name = "Game screen at start")
@Composable
private fun GameScreenStartPreview() {
    Content()
}
