package io.altenems.companion.android.wireguard

import com.wireguard.android.backend.GoBackend

// WireGuardVpnService.kt
class WireGuardVpnService : GoBackend.VpnService() {
    companion object {
        const val ACTION_START = "START_VPN"
        const val ACTION_STOP  = "STOP_VPN"
        const val EXTRA_CONFIG = "CONFIG_PATH"
    }
}
