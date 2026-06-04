package io.altenems.companion.android.onboarding.welcome

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wireguard.config.Config
import dagger.hilt.android.lifecycle.HiltViewModel
import io.altenems.companion.android.common.data.wireguard.WireGuardConfig
import io.altenems.companion.android.common.data.wireguard.WireGuardRepository
import io.altenems.companion.android.wireguard.WireGuardManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val wireGuardRepository: WireGuardRepository,
    private val wireGuardManager: WireGuardManager
) : ViewModel() {

    val wireGuardConfig: StateFlow<WireGuardConfig> = wireGuardRepository.getConfig()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WireGuardConfig())

    private val _validationStatus = MutableStateFlow<ValidationStatus>(ValidationStatus.Idle)
    val validationStatus: StateFlow<ValidationStatus> = _validationStatus.asStateFlow()

    sealed interface ValidationStatus {
        object Idle : ValidationStatus
        object Validating : ValidationStatus
        data class Valid(val fileName: String) : ValidationStatus
        data class Invalid(val error: String) : ValidationStatus
    }

    fun onFilePicked(context: Context, uri: Uri) {
        viewModelScope.launch {
            _validationStatus.value = ValidationStatus.Validating
            try {
                val contentResolver = context.contentResolver
                val fileName = getFileName(context, uri) ?: "wg_tunnel.conf"
                val configText = contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }

                if (configText != null) {
                    // Validate using WireGuard's Config.parse
                    Config.parse(configText.byteInputStream().bufferedReader())

                    wireGuardRepository.updateConfig(
                        WireGuardConfig(
                            fileName = fileName,
                            configText = configText,
                            autoConnect = true
                        )
                    )
                    _validationStatus.value = ValidationStatus.Valid(fileName)
                } else {
                    _validationStatus.value = ValidationStatus.Invalid("Could not read file")
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to validate WireGuard config")
                _validationStatus.value = ValidationStatus.Invalid(e.message ?: "Unknown error")
            }
        }
    }
    fun onAutoConnectClick(context: Context){
        if (_validationStatus.value  !is ValidationStatus.Valid) return;
        viewModelScope.launch {
            val config = wireGuardRepository.getConfig()


        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        var name: String? = null
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    name = cursor.getString(index)
                }
            }
        }
        return name
    }

    fun connect(activity: Activity) {
        val config = wireGuardConfig.value
        val fileName = config.fileName
        val configText = config.configText
        if (fileName != null && configText != null) {
            if (wireGuardManager.prepareVpn(activity, 100)) {
                wireGuardManager.connect(fileName.substringBeforeLast("."), configText)
            }
        }
    }
}
