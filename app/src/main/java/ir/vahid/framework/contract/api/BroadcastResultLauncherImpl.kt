package ir.vahid.framework.contract.api

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

private class BroadcastResultLauncherImpl<I, O>(
    private val context: Context,
    private val contract: BroadcastResultContract<I, O>,
    private val singleShot: Boolean,
    private val exportedOn33Plus: Boolean, // true for system broadcasts (e.g., SMS), false for in-app
    private val onResult: (O) -> Unit,
) : BroadcastResultLauncher<I> {

    private var receiver: BroadcastReceiver? = null

    override fun launch(input: I) {
        if (receiver != null) return
        val filter = contract.createIntentFilter(input)

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                val out = contract.parseResult(ctx, intent) ?: return
                onResult(out)
                if (singleShot) cancel()
            }
        }

        val flags = if (Build.VERSION.SDK_INT >= 33 && exportedOn33Plus)
            ContextCompat.RECEIVER_EXPORTED
        else if (Build.VERSION.SDK_INT >= 33)
            ContextCompat.RECEIVER_NOT_EXPORTED
        else 0

        Log.d("SMS_RX", "Registering dynamic SMS receiver (exported=${flags != 0})")
        ContextCompat.registerReceiver(context, broadcastReceiver, filter, flags)
        receiver = broadcastReceiver
    }

    override fun cancel() {
        receiver?.let {
            runCatching {
                context.unregisterReceiver(it)
            }.onSuccess {
                print("it")
            }.onFailure {
                print("$it")
            }
        }
        receiver = null
    }
}


@Composable
fun <I, O> rememberBroadcastResultLauncher(
    contract: BroadcastResultContract<I, O>,
    singleShot: Boolean = true,
    exportedOn33Plus: Boolean = false, // true for system broadcasts like SMS
    onResult: (O) -> Unit,
): BroadcastResultLauncher<I> {
    val context = LocalContext.current
    // keep the latest callback without recreating the launcher
    val latestCallback by rememberUpdatedState(onResult)

    val launcher = remember(contract, singleShot, exportedOn33Plus, context) {
        BroadcastResultLauncherImpl(
            context = context,
            contract = contract,
            singleShot = singleShot,
            exportedOn33Plus = exportedOn33Plus,
        ) { out ->
            latestCallback(out)
        }
    }

    // tie the receiver's lifetime to this launcher instance
    DisposableEffect(launcher) {
        onDispose {
            launcher.cancel()
        }
    }
    return launcher
}
