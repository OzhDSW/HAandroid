package io.altenems.companion.android.settings.gestures

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import io.altenems.companion.android.common.util.GestureAction
import io.altenems.companion.android.common.util.HAGesture
import io.altenems.companion.android.settings.gestures.views.GestureActionsView
import io.altenems.companion.android.settings.gestures.views.GesturesListView
import io.altenems.companion.android.util.compose.altenemsAppTheme

class GesturesFragmentScreenshotTest {

    @PreviewTest
    @Preview
    @Composable
    fun `Gestures list with no action for each gesture`() {
        altenemsAppTheme {
            GesturesListView(
                gestureActions = HAGesture.entries.associateWith { GestureAction.NONE },
                onGestureClicked = { _ -> },
            )
        }
    }

    @PreviewTest
    @Preview
    @Composable
    fun `Gesture actions with search entities selected`() {
        altenemsAppTheme {
            GestureActionsView(
                selectedAction = GestureAction.QUICKBAR_DEFAULT,
                onActionClicked = {},
            )
        }
    }
}
