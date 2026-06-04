package io.altenems.companion.android.onboarding.sethomenetwork.navigation

import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.altenems.companion.android.common.R as commonR
import io.altenems.companion.android.onboarding.BaseOnboardingNavigationTest
import io.altenems.companion.android.onboarding.locationforsecureconnection.navigation.URL_SECURITY_LEVEL_DOCUMENTATION
import io.altenems.companion.android.testing.unit.stringResource
import io.altenems.companion.android.util.compose.navigateToUri
import io.mockk.coVerify
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Navigation tests for the Set Home Network screen in the onboarding flow.
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
@HiltAndroidTest
internal class SetHomeNetworkNavigationTest : BaseOnboardingNavigationTest() {

    @Test
    fun `Given SetHomeNetwork when clicking next then onboarding completes`() {
        testNavigation {
            navController.navigateToSetHomeNetworkRoute(42)
            assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<SetHomeNetworkRoute>() == true)

            onNodeWithContentDescription(stringResource(commonR.string.get_help)).performClick()
            coVerify { any<NavController>().navigateToUri(URL_SECURITY_LEVEL_DOCUMENTATION, any()) }

            onNodeWithText(stringResource(commonR.string.set_home_network_next))
                .performScrollTo()
                .performClick()
            assertTrue(onboardingDone)
        }
    }
}
