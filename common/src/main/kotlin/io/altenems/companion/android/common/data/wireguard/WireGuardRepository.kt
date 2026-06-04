package io.altenems.companion.android.common.data.wireguard

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.altenems.companion.android.di.qualifiers.NamedWireGuardStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

data class WireGuardConfig(
    val fileName: String? = null,
    val configText: String? = null,
    val autoConnect: Boolean = false
)

interface WireGuardRepository {
    fun getConfig(): Flow<WireGuardConfig>
    suspend fun updateConfig(config: WireGuardConfig)
    suspend fun clearConfig()
}

@Singleton
internal class WireGuardRepositoryImpl @Inject constructor(
    @NamedWireGuardStorage private val dataStore: DataStore<Preferences>
) : WireGuardRepository {

    private object Keys {
        val FILE_NAME = stringPreferencesKey("wg_file_name")
        val CONFIG_TEXT = stringPreferencesKey("wg_config_text")
        val AUTO_CONNECT = booleanPreferencesKey("wg_auto_connect")
    }

    override fun getConfig(): Flow<WireGuardConfig> {
        return dataStore.data.map { preferences ->
            WireGuardConfig(
                fileName = preferences[Keys.FILE_NAME],
                configText = preferences[Keys.CONFIG_TEXT],
                autoConnect = preferences[Keys.AUTO_CONNECT] ?: false
            )
        }
    }

    override suspend fun updateConfig(config: WireGuardConfig) {
        dataStore.edit { preferences ->
            if (config.fileName != null) {
                preferences[Keys.FILE_NAME] = config.fileName
            } else {
                preferences.remove(Keys.FILE_NAME)
            }
            if (config.configText != null) {
                preferences[Keys.CONFIG_TEXT] = config.configText
            } else {
                preferences.remove(Keys.CONFIG_TEXT)
            }
            preferences[Keys.AUTO_CONNECT] = config.autoConnect
        }
    }

    override suspend fun clearConfig() {
        dataStore.edit { it.clear() }
    }
}
