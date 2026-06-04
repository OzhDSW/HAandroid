package io.altenems.companion.android.compose.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.android.tools.screenshot.PreviewTest
import io.altenems.companion.android.common.compose.composable.HASettingsCard
import io.altenems.companion.android.common.compose.theme.HATextStyle
import io.altenems.companion.android.common.compose.theme.HAThemeForPreview

class HASettingsCardScreenshotTest {

    @PreviewLightDark
    @PreviewTest
    @Composable
    fun `Simple HASettingsCard with content`() {
        HAThemeForPreview {
            HASettingsCard {
                Text(text = "Settings card content", style = HATextStyle.Body)
            }
        }
    }
}
