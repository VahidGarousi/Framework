package ir.vahid.framework.contract.screen

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import ir.vahid.framework.contract.api.BroadcastResultContract

// ScreenBroadcastContract.kt
class ScreenBroadcastContract : BroadcastResultContract<Unit, ScreenEvent>() {

    override fun createIntentFilter(input: Unit): IntentFilter =
        IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
        }

    override fun parseResult(context: Context, intent: Intent): ScreenEvent? = when (intent.action) {
        Intent.ACTION_SCREEN_ON  -> ScreenEvent.On
        Intent.ACTION_SCREEN_OFF -> ScreenEvent.Off
        else -> null
    }
}

sealed interface ScreenEvent {
    data object On : ScreenEvent
    data object Off : ScreenEvent
}
