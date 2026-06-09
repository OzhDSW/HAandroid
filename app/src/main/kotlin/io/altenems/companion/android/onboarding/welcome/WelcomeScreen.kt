package io.altenems.companion.android.onboarding.welcome

import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.glance.layout.Row
import androidx.glance.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.altenems.companion.android.R
import io.altenems.companion.android.common.R as commonR
import io.altenems.companion.android.common.compose.composable.ButtonVariant
import io.altenems.companion.android.common.compose.composable.HAAccentButton
import io.altenems.companion.android.common.compose.composable.HAIconButton
import io.altenems.companion.android.common.compose.composable.HAPlainButton
import io.altenems.companion.android.common.compose.theme.HADimens
import io.altenems.companion.android.common.compose.theme.HATextStyle
import io.altenems.companion.android.common.compose.theme.HAThemeForPreview
import io.altenems.companion.android.common.compose.theme.MaxButtonWidth
import io.altenems.companion.android.common.data.wireguard.WireGuardConfig
import io.altenems.companion.android.util.compose.HAPreviews
import io.altenems.companion.android.wireguard.WireGuardManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private val ICON_SIZE = 120.dp
private val MaxTextWidth = MaxButtonWidth

@Composable
internal fun WelcomeScreen(
    onConnectClick: () -> Unit,
    onLearnMoreClick: suspend () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel(),
) {
    val wireGuardConfig by viewModel.wireGuardConfig.collectAsStateWithLifecycle()
    val validationStatus by viewModel.validationStatus.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = LocalActivity.current
    val vpnState by viewModel.isVpnConnected.collectAsStateWithLifecycle()

    LaunchedEffect(wireGuardConfig.fileName) {
        if (wireGuardConfig.fileName != null && validationStatus is WelcomeViewModel.ValidationStatus.Idle)
            viewModel.updateValidationStatus(wireGuardConfig.fileName,wireGuardConfig.configText)
    }
    WelcomeScreenContent(
        wireGuardConfig = wireGuardConfig,
        validationStatus = validationStatus,
        isVpnConnected =  vpnState,
        onConnectClick = onConnectClick,
        onLearnMoreClick = onLearnMoreClick,
        onFilePicked = { uri -> viewModel.onFilePicked(context, uri,activity, true) },
        onConnectVpnClick = { activity?.let { viewModel.connect(it) } },
        modifier = modifier,
    )
}

@Composable
private fun WelcomeScreenContent(
    wireGuardConfig: WireGuardConfig,
    validationStatus: WelcomeViewModel.ValidationStatus,
    isVpnConnected: Boolean,
    onConnectClick: () -> Unit,
    onLearnMoreClick: suspend () -> Unit,
    onFilePicked: (Uri) -> Unit,
    onConnectVpnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        uri?.let { onFilePicked(it) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = HADimens.SPACE4),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(HADimens.SPACE6),
    ) {
        // We use spacer to position the image where we want when there is remaining space in the column using percentage
        val positionPercentage = 0.2f
        Spacer(modifier = Modifier.weight(positionPercentage))

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(commonR.string.branding_icon_content_description),
            modifier = Modifier.size(ICON_SIZE),
        )
        WelcomeText()

        Spacer(modifier = Modifier.weight(1f - positionPercentage))

        WireGuardSection(
            config = wireGuardConfig,
            validationStatus = validationStatus,
            onPickFileClick = { filePickerLauncher.launch("*/*") },
            onConnectVpnClick = onConnectVpnClick,
            isVpnConnected = isVpnConnected,
        )
        BottomButtons(onConnectClick = onConnectClick, onLearnMoreClick = onLearnMoreClick, validationStatus)
    }
}

@Composable
private fun WireGuardSection(
    config: WireGuardConfig,
    validationStatus: WelcomeViewModel.ValidationStatus,
    onPickFileClick: () -> Unit,
    onConnectVpnClick: () -> Unit,
    isVpnConnected: Boolean
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(HADimens.SPACE2),
    ) {
        androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(HADimens.SPACE2, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ){
            var confText = config.fileName
            when (validationStatus) {
                is WelcomeViewModel.ValidationStatus.Valid -> {
                    confText = stringResource(commonR.string.wireguard_valid_config, validationStatus.fileName)
                }
                is WelcomeViewModel.ValidationStatus.Invalid -> {
                    confText = stringResource(commonR.string.wireguard_invalid_config, validationStatus.error)
                }
                WelcomeViewModel.ValidationStatus.Validating -> {
                    confText = stringResource(commonR.string.wireguard_validating)
                }
                WelcomeViewModel.ValidationStatus.Idle -> {}
            }
            if (confText != null){
                Text(
                    text = confText,
                    style = HATextStyle.BodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                HAIconButton(
                    icon = ImageVector.vectorResource(commonR.drawable.ic_edit),
                    onClick = onPickFileClick,
                    contentDescription = "Change VPN config",
                    modifier = Modifier.size(32.dp),
                    variant = ButtonVariant.WARNING,
                )
            }else
            HAPlainButton(
                text = stringResource(commonR.string.wireguard_pick_config),
                onClick = onPickFileClick,
                modifier = Modifier.fillMaxWidth()
            )
        }




//        if (config.configText != null) {
//            HAAccentButton(
//                text = stringResource(
//                    if (!isVpnConnected) commonR.string.wireguard_connect_vpn
//                    else commonR.string.wireguard_disconnect_vpn),
//                onClick = onConnectVpnClick,
//                modifier = Modifier.fillMaxWidth(),
//            )
//        }
    }
}

@Composable
private fun ColumnScope.WelcomeText() {
    Text(
        text = stringResource(commonR.string.welcome_alten_title),
        style = HATextStyle.Headline,
        modifier = Modifier.widthIn(max = MaxTextWidth),
    )

    Text(
        text = stringResource(commonR.string.welcome_details),
        style = HATextStyle.Body,
        modifier = Modifier.widthIn(max = MaxTextWidth),
    )
}

@Composable
private fun BottomButtons(
    onConnectClick: () -> Unit,
    onLearnMoreClick: suspend () -> Unit,
    validationStatus: WelcomeViewModel.ValidationStatus
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(HADimens.SPACE4),
    ) {
        HAAccentButton(
            text = stringResource(commonR.string.welcome_connect_to_ha),
            onClick = onConnectClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = validationStatus !is WelcomeViewModel.ValidationStatus.Invalid
        )

        HAPlainButton(
            text = stringResource(commonR.string.welcome_learn_more),
            onClick = {
                coroutineScope.launch {
                    onLearnMoreClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = HADimens.SPACE6),
        )
    }
}

@HAPreviews
@Composable
private fun WelcomeScreenPreview() {
    val config = WireGuardConfig(fileName = "programmer.conf")
    //val config =  WireGuardConfig()
    val status = if (config.fileName != null ) WelcomeViewModel.ValidationStatus.Valid(config.fileName!!) else WelcomeViewModel.ValidationStatus.Idle
    HAThemeForPreview {
        WelcomeScreenContent(
            wireGuardConfig = config,
            validationStatus = status,
            isVpnConnected = false,
            onConnectClick = {},
            onLearnMoreClick = {},
            onFilePicked = {},
            onConnectVpnClick = {},
        )
    }
}
