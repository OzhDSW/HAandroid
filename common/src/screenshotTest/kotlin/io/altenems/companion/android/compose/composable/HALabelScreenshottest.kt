package io.altenems.companion.android.compose.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.android.tools.screenshot.PreviewTest
import io.altenems.companion.android.common.compose.composable.HALabel
import io.altenems.companion.android.common.compose.composable.LabelSize
import io.altenems.companion.android.common.compose.composable.LabelVariant
import io.altenems.companion.android.common.compose.theme.HADimens
import io.altenems.companion.android.common.compose.theme.HAThemeForPreview

class HALabelScreenshottest {

    @PreviewLightDark
    @PreviewTest
    @Composable
    fun `HALabel variants`() {
        HAThemeForPreview {
            Column(verticalArrangement = Arrangement.spacedBy(HADimens.SPACE2)) {
                LabelVariant.entries.forEach { variant ->
                    HALabel(text = "Label", variant = variant)
                }
                LabelVariant.entries.forEach { variant ->
                    HALabel(text = "Label", variant = variant, size = LabelSize.SMALL)
                }
            }
        }
    }
}
