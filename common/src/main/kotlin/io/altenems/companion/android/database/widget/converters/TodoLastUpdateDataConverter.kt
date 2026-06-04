package io.altenems.companion.android.database.widget.converters

import androidx.room.TypeConverter
import io.altenems.companion.android.common.util.kotlinJsonMapper
import io.altenems.companion.android.database.widget.TodoWidgetEntity

class TodoLastUpdateDataConverter {
    @TypeConverter
    fun fromJson(value: String?): TodoWidgetEntity.LastUpdateData? {
        return value?.let { kotlinJsonMapper.decodeFromString<TodoWidgetEntity.LastUpdateData>(it) }
    }

    @TypeConverter
    fun toJson(data: TodoWidgetEntity.LastUpdateData?): String? {
        return data?.let { kotlinJsonMapper.encodeToString(it) }
    }
}
