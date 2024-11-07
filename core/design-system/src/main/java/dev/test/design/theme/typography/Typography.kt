package dev.test.design.theme.typography

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * This class defines all the typography tokens of the design system.
 */
@Immutable
object Typography{
    val H1: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W700,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
    val H2: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W700,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
    val H3: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W700,
        fontSize = 24.sp,
        lineHeight = 26.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
    val H4: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
    val H5: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
    val H6: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
}

internal val LocalTypography = staticCompositionLocalOf { Typography }
