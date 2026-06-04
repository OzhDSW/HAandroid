package io.altenems.companion.android.loading

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.tools.screenshot.PreviewTest
import io.altenems.companion.android.common.compose.theme.HAThemeForPreview
import io.altenems.companion.android.util.compose.HAPreviews

class LoadingScreenshotTest {

    @PreviewTest
    @HAPreviews
    @Composable
    fun `LoadingScreen`() {
        HAThemeForPreview {
            LoadingScreen(modifier = Modifier)
        }
    }
}
