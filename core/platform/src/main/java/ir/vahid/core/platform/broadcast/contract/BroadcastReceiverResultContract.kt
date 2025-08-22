package ir.vahid.core.platform.broadcast.contract

import android.content.Context
import android.content.Intent
import android.content.IntentFilter

abstract class BroadcastReceiverResultContract<I, O> {
    abstract fun createIntentFilter(input: I): IntentFilter

    abstract fun parseResult(
        context: Context,
        intent: Intent,
    ): O?
}
