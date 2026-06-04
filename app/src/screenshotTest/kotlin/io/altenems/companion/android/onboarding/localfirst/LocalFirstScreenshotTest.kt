package io.altenems.companion.android.onboarding.localfirst

import androidx.compose.runtime.Composable
import com.android.tools.screenshot.PreviewTest
import io.altenems.companion.android.common.compose.theme.HAThemeForPreview
import io.altenems.companion.android.util.compose.HAPreviews

class LocalFirstScreenshotTest {

    @PreviewTest
    @HAPreviews
    @Composable
    fun `LocalFirstContent empty`() {
        HAThemeForPreview {
            LocalFirstScreen(onNextClick = {})
        }
    }
}
