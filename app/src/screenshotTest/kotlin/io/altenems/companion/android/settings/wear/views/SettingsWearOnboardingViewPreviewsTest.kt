package io.altenems.companion.android.settings.wear.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import io.altenems.companion.android.common.R as commonR
import io.altenems.companion.android.util.compose.altenemsAppTheme

class SettingsWearOnboardingViewPreviewsTest {

    @PreviewTest
    @Preview
    @Composable
    private fun `Onboarding View checking for devices`() {
        altenemsAppTheme {
            SettingsWearOnboardingViewContent(
                infoTextTitleResource = commonR.string.message_checking,
                shouldDisplayRemoteAppInstallButton = true,
                onInstallOnWearDeviceClicked = {},
                onBackClicked = {},
            )
        }
    }

    @PreviewTest
    @Preview
    @Composable
    private fun `Onboarding view no devices found`() {
        altenemsAppTheme {
            SettingsWearOnboardingViewContent(
                infoTextTitleResource = commonR.string.message_no_connected_nodes,
                shouldDisplayRemoteAppInstallButton = true,
                onInstallOnWearDeviceClicked = {},
                onBackClicked = {},
            )
        }
    }

    @PreviewTest
    @Preview
    @Composable
    private fun `Onboarding view app missing on all devices`() {
        altenemsAppTheme {
            SettingsWearOnboardingViewContent(
                infoTextTitleResource = commonR.string.message_missing_all,
                shouldDisplayRemoteAppInstallButton = true,
                onInstallOnWearDeviceClicked = {},
                onBackClicked = {},
            )
        }
    }
}
