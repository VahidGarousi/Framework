package ir.vahid.core.platform.broadcast.contract

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat

/**
 * Internal implementation of [BroadcastResultLauncher].
 */
class BroadcastReceiverResultLauncherImpl<I, O>(
    private val context: Context,
    private val contract: BroadcastReceiverResultContract<I, O>,
    private val exportedOn33Plus: Boolean, // true for system broadcasts (e.g., SMS), false for in-app
    private val onResult: (O) -> Unit,
) : BroadcastResultLauncher<I> {
    private var receiver: BroadcastReceiver? = null

    override fun launch(input: I) {
        if (receiver != null) return
        val filter = contract.createIntentFilter(input)
        val broadcastReceiver = createReceiver()
        val flags = calculateFlags()
        ContextCompat.registerReceiver(context, broadcastReceiver, filter, flags)
        receiver = broadcastReceiver
    }

    @VisibleForTesting
    internal fun createReceiver(): BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(
                context: Context,
                intent: Intent,
            ) {
                contract.parseResult(context, intent)?.let(onResult)
            }
        }

    @VisibleForTesting
    internal fun calculateFlags(): Int =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && exportedOn33Plus -> ContextCompat.RECEIVER_EXPORTED
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> ContextCompat.RECEIVER_NOT_EXPORTED
            else -> FLAGS_NONE
        }

    private fun safeUnregister(receiver: BroadcastReceiver) {
        runCatching { context.unregisterReceiver(receiver) }.onFailure {
            // ignore
        }
    }

    override fun cancel() {
        receiver?.let { safeUnregister(it) }
        receiver = null
    }
}

private const val FLAGS_NONE = 0
