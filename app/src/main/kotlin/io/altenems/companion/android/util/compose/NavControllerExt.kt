package io.altenems.companion.android.util.compose

import androidx.navigation.NavController
import io.altenems.companion.android.common.util.openUri

suspend fun NavController.navigateToUri(
    uri: String,
    onShowSnackbar: suspend (message: String, action: String?) -> Boolean,
) {
    context.openUri(uri, onShowSnackbar)
}
