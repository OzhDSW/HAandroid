package io.altenems.companion.android.widgets.todo

import io.altenems.companion.android.common.data.integration.Entity
import java.time.LocalDateTime

internal fun fakeServerEntity(entityId: String, friendlyName: String? = null): Entity {
    return Entity(
        entityId = entityId,
        state = "",
        attributes = if (friendlyName != null) mapOf("friendly_name" to friendlyName) else emptyMap(),
        lastChanged = LocalDateTime.now(),
        lastUpdated = LocalDateTime.now(),
    )
}
