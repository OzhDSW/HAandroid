package io.altenems.companion.android.onboarding.locationsharing

import androidx.compose.runtime.Composable
import com.android.tools.screenshot.PreviewTest
import io.altenems.companion.android.common.compose.theme.HAThemeForPreview
import io.altenems.companion.android.util.compose.HAPreviews

class LocationSharingScreenshotTest {

    @PreviewTest
    @HAPreviews
    @Composable
    fun `LocationSharing empty`() {
        HAThemeForPreview {
            LocationSharingScreen(
                onHelpClick = {},
                onGoToNextScreen = {},
                onLocationSharingResponse = {},
            )
        }
    }
}
