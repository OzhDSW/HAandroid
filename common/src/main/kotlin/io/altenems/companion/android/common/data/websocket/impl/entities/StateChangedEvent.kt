package io.altenems.companion.android.common.data.websocket.impl.entities

import io.altenems.companion.android.common.data.integration.Entity
import kotlinx.serialization.Serializable

@Serializable
data class StateChangedEvent(val entityId: String, val oldState: Entity? = null, val newState: Entity? = null)
