package io.altenems.companion.android.onboarding

import androidx.annotation.StringRes
import io.altenems.companion.android.database.server.TemporaryServer

interface OnboardingView {
    fun startIntegration(temporaryServer: TemporaryServer)

    fun onInstanceFound(instance: altenemsInstance)
    fun onInstanceLost(instance: altenemsInstance)

    fun showLoading()

    fun showContinueOnPhone()

    fun showError(@StringRes message: Int? = null)
}
