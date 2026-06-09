package io.altenems.companion.android.loading

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import io.altenems.companion.android.R
import io.altenems.companion.android.common.R as commonR
import io.altenems.companion.android.common.compose.composable.HALoading
import io.altenems.companion.android.common.compose.theme.HAThemeForPreview
import io.altenems.companion.android.util.compose.HAPreviews

private val ICON_SIZE = 240.dp

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(ICON_SIZE),
        )
        val contentDescriptionLoading = stringResource(commonR.string.loading_content_description)
        HALoading(
            modifier = Modifier
                .padding(bottom = maxHeight / 8)
                .align(Alignment.BottomCenter)
                .semantics {
                    contentDescription = contentDescriptionLoading
                },
        )
    }
}

@HAPreviews
@Composable
private fun LoadingScreenPreview() {
    HAThemeForPreview {
        LoadingScreen()
    }
}
