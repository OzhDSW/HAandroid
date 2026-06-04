package io.altenems.companion.android.onboarding.integration

import io.altenems.companion.android.database.server.TemporaryServer

interface MobileAppIntegrationPresenter {
    fun onRegistrationAttempt(temporaryServer: TemporaryServer, deviceName: String)
    fun onFinish()
}
