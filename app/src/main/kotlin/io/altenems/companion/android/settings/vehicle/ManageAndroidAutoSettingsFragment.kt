package io.altenems.companion.android.settings.vehicle

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.altenems.companion.android.common.R as commonR
import io.altenems.companion.android.common.util.isAutomotive
import io.altenems.companion.android.settings.addHelpMenuProvider
import io.altenems.companion.android.settings.vehicle.views.AndroidAutoFavoritesSettings
import io.altenems.companion.android.util.compose.altenemsAppTheme

@AndroidEntryPoint
class ManageAndroidAutoSettingsFragment : Fragment() {

    val viewModel: ManageAndroidAutoViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                altenemsAppTheme {
                    AndroidAutoFavoritesSettings(
                        androidAutoViewModel = viewModel,
                        serversList = viewModel.servers,
                        defaultServer = viewModel.defaultServerId,
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addHelpMenuProvider("https://companion.home-assistant.io/docs/android-auto")
    }

    override fun onResume() {
        super.onResume()
        activity?.title =
            if (requireContext().isAutomotive()) {
                getString(commonR.string.android_automotive_favorites)
            } else {
                getString(commonR.string.aa_favorites)
            }
    }
}
