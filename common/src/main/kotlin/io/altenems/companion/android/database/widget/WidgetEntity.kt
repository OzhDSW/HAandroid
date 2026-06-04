package io.altenems.companion.android.database.widget

import java.io.Serializable

sealed interface WidgetEntity<T : WidgetEntity<T>> : Serializable {
    val id: Int
    val serverId: Int

    fun copyWithWidgetId(appWidgetId: Int): T
}
