package io.altenems.companion.android.sensors

import android.content.Context
import io.altenems.companion.android.common.R as commonR
import io.altenems.companion.android.common.sensors.SensorManager

class AndroidAutoSensorManager : SensorManager {

    override val name: Int
        get() = commonR.string.sensor_name_android_auto

    override suspend fun getAvailableSensors(context: Context): List<SensorManager.BasicSensor> {
        return listOf()
    }

    override fun requiredPermissions(context: Context, sensorId: String): Array<String> {
        return emptyArray()
    }

    override suspend fun requestSensorUpdate(context: Context) {
        // Noop
    }
}
