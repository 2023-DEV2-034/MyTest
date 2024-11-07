package dev.test.design.theme.color

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * This class defines all the color tokens of the design system.
 */
@Stable
class ColorScheme(
    surfacePrimary: Color,
    surfaceSecondary: Color,
    textPrimary: Color,
    textSecondary: Color,
    iconPrimary: Color,
    iconSecondary: Color,
) {
    var surfacePrimary by mutableStateOf(surfacePrimary)
        internal set
    var surfaceSecondary by mutableStateOf(surfaceSecondary)
        internal set
    var textPrimary by mutableStateOf(textPrimary)
        internal set
    var textSecondary by mutableStateOf(textSecondary)
        internal set
    var iconPrimary by mutableStateOf(iconPrimary)
        internal set
    var iconSecondary by mutableStateOf(iconSecondary)
        internal set

    companion object {
        fun colors() = ColorScheme(
            surfacePrimary = Palette.Black900,
            surfaceSecondary = Palette.Black800,
            textPrimary = Palette.White,
            textSecondary = Palette.Black100,
            iconPrimary = Palette.Mustard400,
            iconSecondary = Palette.Turquoise400,
        )
    }
}

 val LocalColorScheme = staticCompositionLocalOf { ColorScheme.colors() }
