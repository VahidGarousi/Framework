package ir.vahid.framework.contract.api

import android.content.Context
import android.content.Intent
import android.content.IntentFilter

abstract class BroadcastResultContract<I, O> {
    abstract fun createIntentFilter(input: I): IntentFilter
    abstract fun parseResult(context: Context, intent: Intent): O?
}
