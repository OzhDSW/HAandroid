package io.altenems.companion.android.wireguard

import android.app.Activity
import android.app.Service
import android.content.Context
import android.net.Uri
import android.util.Log
import  com.wireguard.android.backend.Tunnel
import com.wireguard.android.backend.GoBackend
import com.wireguard.config.Config
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class WireGuardManager(private val context: Context) {

    private val backend = GoBackend(context)
    private var activeTunnel: Tunnel? = null
    var tunnelState: Tunnel.State = Tunnel.State.DOWN
    // A minimal Tunnel implementation
    private inner class SimpleTunnel(private val name: String) : Tunnel {
        override fun getName() = name
        override fun onStateChange(newState: Tunnel.State) {
            Timber.tag("WireGuard").d("Tunnel $name state: $newState")
        }
    }

    /** Request VPN permission — call this before connect() */
    fun prepareVpn(activity: Activity, requestCode: Int): Boolean {
        val intent = GoBackend.VpnService.prepare(context)
        return if (intent != null) {
            activity.startActivityForResult(intent, requestCode)
            false // permission dialog shown, wait for result
        } else {
            true  // already granted
        }
    }

    /** Connect using a .conf File */
    fun connect(configFile: File) {
        val config = WireGuardConfigLoader.loadFromFile(configFile)
        connectWithConfig(configFile.nameWithoutExtension, config)
    }

    /** Connect using a Uri (e.g. from file picker) */
    fun connect(uri: Uri) {
        val config = WireGuardConfigLoader.loadFromUri(context, uri)
        connectWithConfig("wg_tunnel", config)
    }

    /** Connect using config text */
    fun connect(name: String, configText: String) {
        val config = WireGuardConfigLoader.loadFromText(configText)
        connectWithConfig(name, config)
    }

    private fun connectWithConfig(name: String, config: Config) {
        val tunnel = SimpleTunnel(name)
        activeTunnel = tunnel

        CoroutineScope(Dispatchers.IO).launch {
            try {
                backend.setState(tunnel, Tunnel.State.UP, config)
                Timber.tag("WireGuard").d("Connected!")
            } catch (e: Exception) {
                Timber.tag("WireGuard").e("Failed to connect: ${e.message}")
            }
        }
    }

    fun disconnect() {
        val tunnel = activeTunnel ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                backend.setState(tunnel, Tunnel.State.DOWN, null)
                activeTunnel = null
                Timber.tag("WireGuard").d("Disconnected!")
            } catch (e: Exception) {
                Timber.tag("WireGuard").e("Failed to disconnect: ${e.message}")
            }
        }
    }

    fun isConnected(): Boolean {
        return try {
            activeTunnel?.let {
                backend.getState(it) == Tunnel.State.UP
            } ?: false
        } catch (e: Exception) { false }
    }
    object WireGuardConfigLoader {

        fun loadFromFile(file: File): Config {
            return file.bufferedReader().use { reader ->
                Config.parse(reader)
            }
        }

        fun loadFromUri(context: Context, uri: Uri): Config {
            return context.contentResolver.openInputStream(uri)
                ?.bufferedReader()
                ?.use { reader -> Config.parse(reader) }
                ?: error("Cannot open config file")
        }

        fun loadFromText(text: String): Config {
            return text.byteInputStream().bufferedReader().use { reader ->
                Config.parse(reader)
            }
        }
    }
}
