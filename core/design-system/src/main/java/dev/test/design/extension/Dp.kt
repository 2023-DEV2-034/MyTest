package dev.test.design.extension

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * Converts a [Dp] value to a [Shape] with rounded corners.
 *
 * Example usage:
 * ```
 * val shape: Shape = 16.dp.toRoundShape
 * ```
 *
 * @receiver [Dp] The dimension value used to create the [RoundedCornerShape].
 * @return [Shape] A [RoundedCornerShape] with the specified corner radius.
 */
val Dp.toRoundShape: Shape
    get() = RoundedCornerShape(this)
