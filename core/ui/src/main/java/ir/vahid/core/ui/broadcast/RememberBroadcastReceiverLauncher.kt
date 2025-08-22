package ir.vahid.core.ui.broadcast

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import ir.vahid.core.platform.broadcast.contract.BroadcastReceiverResultContract
import ir.vahid.core.platform.broadcast.contract.BroadcastReceiverResultLauncherImpl
import ir.vahid.core.platform.broadcast.contract.BroadcastResultLauncher

/**
 * Remember a [BroadcastResultLauncher] tied to the current [Composable] lifecycle.
 *
 * Similar to rememberLauncherForActivityResult, but for broadcast contracts.
 *
 * @param contract the broadcast contract to use
 * @param singleShot whether the receiver should auto-unregister after first result
 * @param exportedOn33Plus whether the receiver should be exported (system broadcasts)
 * @param onResult callback for each received result
 */
@Composable
fun <I, O> rememberBroadcastReceiverResultLauncher(
    contract: BroadcastReceiverResultContract<I, O>,
    exportedOn33Plus: Boolean = false, // true for system broadcasts like SMS
    onResult: (O) -> Unit,
): BroadcastResultLauncher<I> {
    val context: Context = LocalContext.current
    val latestCallback by rememberUpdatedState(onResult)

    val launcher = remember(contract, exportedOn33Plus, context) {
        BroadcastReceiverResultLauncherImpl(
            context = context,
            contract = contract,
            exportedOn33Plus = exportedOn33Plus,
        ) { out ->
            latestCallback(out)
        }
    }

    // Automatically unregister when Composable leaves composition
    DisposableEffect(launcher) {
        onDispose {
            launcher.cancel()
        }
    }
    return launcher
}
