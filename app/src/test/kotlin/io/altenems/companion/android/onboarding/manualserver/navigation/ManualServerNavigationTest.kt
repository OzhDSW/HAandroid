package io.altenems.companion.android.onboarding.manualserver.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.altenems.companion.android.common.R as commonR
import io.altenems.companion.android.onboarding.BaseOnboardingNavigationTest
import io.altenems.companion.android.onboarding.URL_GETTING_STARTED_DOCUMENTATION
import io.altenems.companion.android.onboarding.connection.CONNECTION_SCREEN_TAG
import io.altenems.companion.android.onboarding.connection.navigation.ConnectionRoute
import io.altenems.companion.android.onboarding.serverdiscovery.navigation.ServerDiscoveryRoute
import io.altenems.companion.android.onboarding.serverdiscovery.navigation.navigateToServerDiscovery
import io.altenems.companion.android.testing.unit.stringResource
import io.altenems.companion.android.util.compose.navigateToUri
import io.mockk.coVerify
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Navigation tests for the Manual Server screen in the onboarding flow.
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
@HiltAndroidTest
internal class ManualServerNavigationTest : BaseOnboardingNavigationTest() {

    @Test
    fun `Given enter manual address when setting url and clicking connect then show ConnectScreen then back goes to ManualServer`() {
        testNavigation {
            navController.navigateToServerDiscovery()
            assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<ServerDiscoveryRoute>() == true)
            onNodeWithText(stringResource(commonR.string.manual_setup))
                .performScrollTo()
                .assertIsDisplayed()
                .performClick()

            assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<ManualServerRoute>() == true)

            onNodeWithContentDescription(stringResource(commonR.string.get_help)).performClick()
            coVerify { any<NavController>().navigateToUri(URL_GETTING_STARTED_DOCUMENTATION, any()) }

            onNodeWithText("http://altenems.local:8123").performTextInput("http://ha.local")

            onNodeWithText(stringResource(commonR.string.connect))
                .performScrollTo()
                .assertIsDisplayed()
                .assertIsEnabled()
                .performClick()

            assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<ConnectionRoute>() == true)
            onNodeWithTag(CONNECTION_SCREEN_TAG).assertIsDisplayed()

            composeTestRule.activity.onBackPressedDispatcher.onBackPressed()

            waitForIdle()

            assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<ManualServerRoute>() == true)
        }
    }
}
