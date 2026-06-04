package io.altenems.companion.android.onboarding.sethomenetwork

import androidx.compose.runtime.Composable
import com.android.tools.screenshot.PreviewTest
import io.altenems.companion.android.common.compose.theme.HAThemeForPreview
import io.altenems.companion.android.util.compose.HAPreviews

class SetHomeNetworkScreenshotTest {
    @PreviewTest
    @HAPreviews
    @Composable
    fun `SetHomeNetworkScreen with VPN and Ethernet`() {
        HAThemeForPreview {
            SetHomeNetworkScreen(
                onHelpClick = {},
                showEthernet = true,
                showVpn = true,
                isUsingVpn = true,
                isUsingEthernet = true,
                currentWifiNetwork = "altenems-5G",
                onCurrentWifiNetworkChange = {},
                onUsingVpnChange = {},
                onUsingEthernetChange = {},
                onNextClick = {},
            )
        }
    }

    @PreviewTest
    @HAPreviews
    @Composable
    fun `SetHomeNetworkScreen with no VPN and no Ethernet`() {
        HAThemeForPreview {
            SetHomeNetworkScreen(
                onHelpClick = {},
                showEthernet = false,
                showVpn = false,
                isUsingVpn = false,
                isUsingEthernet = false,
                currentWifiNetwork = "",
                onCurrentWifiNetworkChange = {},
                onUsingVpnChange = {},
                onUsingEthernetChange = {},
                onNextClick = {},
            )
        }
    }
}
