package io.altenems.companion.android.util.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceTheme
import androidx.glance.color.ColorProviders
import androidx.glance.material.ColorProviders
import androidx.glance.text.FontWeight
import androidx.glance.text.TextStyle
import io.altenems.companion.android.common.compose.theme.DarkHAColorScheme
import io.altenems.companion.android.common.compose.theme.LightHAColorScheme
import io.altenems.companion.android.common.compose.theme.LocalHAColorScheme

val colorPrimary = Color(0xFF03A9F4)
val colorPrimaryDark = Color(0xFF0288D1)
val darkColorBackground = Color(0xFF1C1C1C)

const val STEP_SCREEN_MAX_WIDTH_DP = 600.0f

private val haLightColors = lightColors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryDark,
    secondary = colorPrimary,
    secondaryVariant = colorPrimary,
    onPrimary = Color.White,
    onSecondary = Color.White,
)
private val haDarkColors = darkColors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryDark,
    secondary = colorPrimary,
    secondaryVariant = colorPrimary,
    background = darkColorBackground,
    onPrimary = Color.White,
    onSecondary = Color.White,
)

/**
 * A Compose [MaterialTheme] version of the app's XML theme. This achieves the same goal as the
 * (now deprecated) [com.google.accompanist.themeadapter.material.MdcTheme].
 */
@Deprecated(
    "Uses Material Design 2. Use HATheme (Material Design 3) instead. Kept until remaining call sites are migrated.",
)
@Composable
fun altenemsAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) haDarkColors else haLightColors,
    ) {
        // Copied from MdcTheme:
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colors.onBackground,
            // To be able to use HA composable in old theme
            LocalHAColorScheme provides if (isSystemInDarkTheme()) DarkHAColorScheme else LightHAColorScheme,
            content = content,
        )
    }
}

@Composable
fun altenemsGlanceTheme(
    colors: ColorProviders = altenemsGlanceTheme.colors,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalGlanceColors provides colors,
    ) {
        GlanceTheme(colors = LocalGlanceColors.current, content = content)
    }
}

object altenemsGlanceTypography {
    val titleLarge: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = altenemsGlanceTheme.colors.onSurface,
        )
    val titleMedium: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = altenemsGlanceTheme.colors.onSurface,
        )
    val titleSmall: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = altenemsGlanceTheme.colors.onSurface,
        )
    val bodyLarge: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = altenemsGlanceTheme.colors.onSurface,
        )
    val bodyMedium: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = altenemsGlanceTheme.colors.onSurface,
        )
    val bodySmall: TextStyle
        @Composable
        get() = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = altenemsGlanceTheme.colors.onSurface,
        )
}

object altenemsGlanceDimensions {
    val iconSize: Dp
        @Composable
        get() = 48.dp
}

val glanceHaLightColors = lightColors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryDark,
    secondary = colorPrimary,
    secondaryVariant = colorPrimary,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = darkColorBackground,
)

val glanceHaDarkColors = darkColors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryDark,
    secondary = colorPrimary,
    secondaryVariant = colorPrimary,
    background = darkColorBackground,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.White,
)

internal val LocalGlanceColors: ProvidableCompositionLocal<ColorProviders> = staticCompositionLocalOf {
    ColorProviders(glanceHaLightColors, glanceHaDarkColors)
}

object altenemsGlanceTheme {
    val colors: ColorProviders
        @Composable
        get() = LocalGlanceColors.current
    val typography: altenemsGlanceTypography
        @Composable
        get() = altenemsGlanceTypography
    val dimensions: altenemsGlanceDimensions
        @Composable
        get() = altenemsGlanceDimensions
}
