package ir.vahid.framework.contract.sms.new

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import ir.vahid.framework.contract.api.BroadcastResultContract

sealed class SmsConsentEvent {
    data class Request(val consentIntent: Intent) : SmsConsentEvent()
    object Timeout : SmsConsentEvent()
}

/**
 * Listens for the Consent broadcast and emits a Request(consentIntent) you must launch.
 * (The SMS text is NOT in this broadcast.)
 */
class SmsUserConsentBroadcastContract :
    BroadcastResultContract<Unit, SmsConsentEvent>() {

    override fun createIntentFilter(input: Unit): IntentFilter {
        return IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
    }

    override fun parseResult(context: Context, intent: Intent): SmsConsentEvent? {
        if (intent.action != SmsRetriever.SMS_RETRIEVED_ACTION) return null
        val extras = intent.extras ?: return null
        val status = extras[SmsRetriever.EXTRA_STATUS] as? Status ?: return null

        return when (status.statusCode) {
            CommonStatusCodes.SUCCESS -> {
                val consent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                consent?.let { SmsConsentEvent.Request(it) }
            }
            CommonStatusCodes.TIMEOUT -> SmsConsentEvent.Timeout
            else -> null
        }
    }
}

/**
 * Pass a specific sender phone/short code if you want to restrict; null listens to any.
 */
fun Context.startSmsUserConsent(sender: String? = null) {
    SmsRetriever.getClient(this).startSmsUserConsent(sender)
}

