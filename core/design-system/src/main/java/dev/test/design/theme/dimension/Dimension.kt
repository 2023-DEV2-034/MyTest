package dev.test.design.theme.dimension

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * This class encapsulates all the different dimension-related tokens of the design system.
 */
@Immutable
object Dimension{
    val size: Size = Size
    val spacing: Spacing = Spacing
    val cornerRadius: CornerRadius = CornerRadius
}

internal val LocalDimensions = staticCompositionLocalOf { Dimension }
