package io.altenems.companion.android.onboarding.welcome

import androidx.compose.runtime.Composable
import com.android.tools.screenshot.PreviewTest
import io.altenems.companion.android.common.compose.theme.HAThemeForPreview
import io.altenems.companion.android.util.compose.HAPreviews

class WelcomeScreenshotTest {

    @PreviewTest
    @HAPreviews
    @Composable
    fun `WelcomeScreen`() {
        HAThemeForPreview {
            WelcomeScreen(onConnectClick = {}, onLearnMoreClick = {})
        }
    }
}
