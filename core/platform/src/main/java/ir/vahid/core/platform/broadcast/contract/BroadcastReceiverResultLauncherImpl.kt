package ir.vahid.core.platform.broadcast.contract

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
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

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context,
                intent: Intent,
            ) {
                val result = contract.parseResult(context, intent) ?: return
                onResult(result)
            }
        }
        val flags = when {
            Build.VERSION.SDK_INT >= 33 && exportedOn33Plus -> ContextCompat.RECEIVER_EXPORTED
            Build.VERSION.SDK_INT >= 33 -> ContextCompat.RECEIVER_NOT_EXPORTED
            else -> 0
        }
        ContextCompat.registerReceiver(context, broadcastReceiver, filter, flags)
        receiver = broadcastReceiver
    }

    override fun cancel() {
        receiver?.let {
            runCatching { context.unregisterReceiver(it) }
        }
        receiver = null
    }
}
