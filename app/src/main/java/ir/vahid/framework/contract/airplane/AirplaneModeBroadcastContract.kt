package ir.vahid.framework.contract.airplane

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import ir.vahid.framework.contract.api.BroadcastResultContract

/**
 * ActivityResultContract-like “broadcast contract” for airplane-mode changes.
 * Output: true = airplane mode ON, false = OFF.
 */
class AirplaneModeBroadcastContract : BroadcastResultContract<Unit, Boolean>() {

    override fun createIntentFilter(input: Unit): IntentFilter =
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)

    override fun parseResult(context: Context, intent: Intent): Boolean? {
        if (intent.action != Intent.ACTION_AIRPLANE_MODE_CHANGED) return null
        // System puts a boolean extra named "state"
        return intent.getBooleanExtra("state", false)
    }
}
