package io.altenems.companion.android.loading.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.altenems.companion.android.loading.LoadingScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoadingRoute

fun NavController.navigateToLoading(navOptions: NavOptions? = null) {
    navigate(LoadingRoute, navOptions)
}

fun NavGraphBuilder.loadingScreen() {
    composable<LoadingRoute> {
        LoadingScreen()
    }
}
