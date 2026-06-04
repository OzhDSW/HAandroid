package io.altenems.companion.android.sensors

import io.altenems.companion.android.BuildConfig
import io.altenems.companion.android.common.sensors.AppSensorManagerBase

class AppSensorManager : AppSensorManagerBase() {

    override fun getCurrentVersion(): String = BuildConfig.VERSION_NAME
}
