package io.altenems.companion.android.settings.server

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.altenems.companion.android.common.data.servers.ServerManager
import io.altenems.companion.android.util.compose.altenemsAppTheme
import io.altenems.companion.android.util.setLayoutAndExpandedByDefault
import javax.inject.Inject

@AndroidEntryPoint
class ServerChooserFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var serverManager: ServerManager

    companion object {
        const val TAG = "ServerChooser"

        const val RESULT_KEY = "ServerChooserResult"
        const val RESULT_SERVER = "server"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val servers by produceState(initialValue = emptyList()) {
                    value = serverManager.servers()
                }
                altenemsAppTheme {
                    ServerChooserView(
                        servers = servers,
                        onServerSelected = { serverId ->
                            setFragmentResult(RESULT_KEY, bundleOf(RESULT_SERVER to serverId))
                            dismiss()
                        },
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutAndExpandedByDefault()
    }
}
