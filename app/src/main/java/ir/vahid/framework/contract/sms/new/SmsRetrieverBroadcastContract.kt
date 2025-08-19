package ir.vahid.framework.contract.sms.new

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import ir.vahid.framework.contract.api.BroadcastResultContract

/**
 * Dynamic receiver contract for Play Services SMS Retriever (no user consent dialog).
 * - No SMS permissions needed.
 * - Requires app hash in the SMS body.
 * Output: the extracted OTP, or null if not found / timeout.
 */
class SmsRetrieverBroadcastContract : BroadcastResultContract<Unit, String>() {

    override fun createIntentFilter(input: Unit): IntentFilter {
        return IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
    }

    override fun parseResult(context: Context, intent: Intent): String? {
        if (intent.action != SmsRetriever.SMS_RETRIEVED_ACTION) return null
        val extras = intent.extras ?: return null
        val status = extras[SmsRetriever.EXTRA_STATUS] as? Status ?: return null

        return when (status.statusCode) {
            CommonStatusCodes.SUCCESS -> {
                val msg = extras.getString(SmsRetriever.EXTRA_SMS_MESSAGE) ?: return null
                OtpExtractor.from(msg)
            }

            CommonStatusCodes.TIMEOUT -> null
            else -> null
        }
    }
}


fun Context.startSmsRetriever() {
    SmsRetriever.getClient(this).startSmsRetriever()
}

