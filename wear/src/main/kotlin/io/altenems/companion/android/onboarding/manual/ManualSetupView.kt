package io.altenems.companion.android.onboarding.manual

import androidx.annotation.StringRes
import io.altenems.companion.android.database.server.TemporaryServer

interface ManualSetupView {
    fun startIntegration(temporaryServer: TemporaryServer)

    fun showLoading()

    fun showContinueOnPhone()

    fun showError(@StringRes message: Int)
}
