package ir.vahid.core.platform.broadcast.contract.system

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import ir.vahid.core.platform.broadcast.contract.BroadcastReceiverResultContract
import ir.vahid.core.platform.broadcast.model.ScreenState

class ScreenStateContractReceiver : BroadcastReceiverResultContract<Unit, ScreenState>() {
    override fun createIntentFilter(input: Unit): IntentFilter =
        IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
        }

    override fun parseResult(
        context: Context,
        intent: Intent,
    ): ScreenState? =
        when (intent.action) {
            Intent.ACTION_SCREEN_ON -> ScreenState.On
            Intent.ACTION_SCREEN_OFF -> ScreenState.Off
            else -> null
        }
}
