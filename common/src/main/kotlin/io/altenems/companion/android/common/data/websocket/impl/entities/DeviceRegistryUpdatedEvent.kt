package io.altenems.companion.android.common.data.websocket.impl.entities

import kotlinx.serialization.Serializable

@Serializable
data class DeviceRegistryUpdatedEvent(val action: String, val deviceId: String)
