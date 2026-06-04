package io.altenems.companion.android.frontend.handler

import io.altenems.companion.android.frontend.WebViewAction
import kotlinx.coroutines.flow.Flow

/**
 * Observes the frontend external bus for incoming events and WebView actions.
 *
 * This interface exposes the ViewModel-facing API of [FrontendMessageHandler],
 * separating it from the bridge-facing [io.altenems.companion.android.frontend.js.FrontendJsHandler].
 */
interface FrontendBusObserver {

    /**
     * Flow of events from incoming external bus messages and authentication results.
     */
    fun messageResults(): Flow<FrontendHandlerEvent>

    /**
     * Returns a flow of [WebViewAction] to be executed by the WebView.
     *
     * The WebView should collect this flow and execute each action accordingly.
     */
    fun webViewActions(): Flow<WebViewAction>
}
