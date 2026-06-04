package io.altenems.companion.android.util.compose

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import io.altenems.companion.android.common.compose.composable.AlphaKey

fun SemanticsNodeInteraction.assertAlpha(alpha: Float): SemanticsNodeInteraction {
    return assert(SemanticsMatcher.expectValue(AlphaKey, alpha))
}
