package ir.vahid.core.platform.broadcast.contract.system

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import ir.vahid.core.platform.broadcast.contract.BroadcastReceiverResultContract

/**
 * ActivityResultContract-like “broadcast contract” for airplane-mode changes.
 * Output: true = airplane mode ON, false = OFF.
 */
class AirplaneModeBroadcastReceiverContract : BroadcastReceiverResultContract<Unit, Boolean>() {
    override fun createIntentFilter(input: Unit): IntentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)

    override fun parseResult(
        context: Context,
        intent: Intent,
    ): Boolean? {
        if (intent.action != Intent.ACTION_AIRPLANE_MODE_CHANGED) return null
        // System puts a boolean extra named "state"
        return intent.getBooleanExtra("state", false)
    }
}
