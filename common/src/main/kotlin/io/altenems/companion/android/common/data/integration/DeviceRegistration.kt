package io.altenems.companion.android.common.data.integration

import io.altenems.companion.android.common.util.AppVersion
import io.altenems.companion.android.common.util.MessagingToken
import javax.inject.Qualifier

data class DeviceRegistration(
    val appVersion: AppVersion? = null,
    val deviceName: String? = null,
    var pushToken: MessagingToken? = null,
    var pushWebsocket: Boolean = true,
)

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PushWebsocketSupport
