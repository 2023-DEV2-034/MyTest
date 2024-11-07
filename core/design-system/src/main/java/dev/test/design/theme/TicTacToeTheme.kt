package dev.test.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import dev.test.design.theme.color.ColorScheme
import dev.test.design.theme.color.LocalColorScheme
import dev.test.design.theme.dimension.Dimension
import dev.test.design.theme.dimension.LocalDimensions
import dev.test.design.theme.typography.LocalTypography
import dev.test.design.theme.typography.Typography

@Composable
fun TicTacToeTheme(
    colors: ColorScheme = TicTacToeTheme.colors,
    dimensions: Dimension = TicTacToeTheme.dimensions,
    typography: Typography = TicTacToeTheme.typography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColorScheme provides colors,
        LocalDimensions provides dimensions,
        LocalTypography provides typography,
        content = content
    )
}

/**
 * This object defines the theme following the specification
 * of the design system.
 * It contains all the design tokens needed for the UI theming.
 */
object TicTacToeTheme {

    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val dimensions: Dimension
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}
