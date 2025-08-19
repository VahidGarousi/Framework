package ir.vahid.framework.contract.sms.new

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import ir.vahid.framework.contract.api.rememberBroadcastResultLauncher


@Composable
fun OtpAutoReadExample(
    onCode: (String) -> Unit,
) {
    val context = LocalContext.current

    // --- Retriever (no UI) ---
    val retriever = rememberBroadcastResultLauncher(
        contract = SmsRetrieverBroadcastContract(),
        singleShot = false,
        exportedOn33Plus = true, // system broadcast
        onResult = { code ->
            onCode(code)
        },
    )

    // Start the background retriever whenever you need it:
    DisposableEffect(Unit) {
        context.startSmsRetriever()
        retriever.launch(Unit)
        onDispose { /* your lifecycle cleanup is handled by rememberBroadcastResultLauncher */ }
    }
}
