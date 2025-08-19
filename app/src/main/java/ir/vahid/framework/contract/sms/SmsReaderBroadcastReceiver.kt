package ir.vahid.framework.contract.sms

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsMessage
import ir.vahid.framework.contract.api.BroadcastResultContract

/**
 * Dynamic receiver contract for SMS OTP messages.
 * - Relies ONLY on Android framework broadcasts (no Play Services).
 * - Expects messages like:
 *   "<#> Your verification code is: 123456\nFA+9qCX9VSu"
 * - Output is the extracted code as String (e.g., "123456"), or null if not found.
 *
 * Requires android.permission.RECEIVE_SMS in the manifest.
 * On Android 13+ (API 33), when registering dynamically, use RECEIVER_EXPORTED.
 */
class SmsReaderBroadcastReceiver : BroadcastResultContract<Unit, String>() {

    override fun createIntentFilter(input: Unit): IntentFilter {
        return IntentFilter(android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
    }

    override fun parseResult(context: Context, intent: Intent): String? {
        if (intent.action != android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return null

        val bundle = intent.extras ?: return null
        val pdus = bundle.get("pdus") as? Array<*> ?: return null
        val format = bundle.getString("format")

        val messages = pdus.mapNotNull { pdu ->
            val bytes = pdu as? ByteArray ?: return@mapNotNull null
            SmsMessage.createFromPdu(bytes, format)
        }

        if (messages.isEmpty()) return null

        val fullMessage = messages.joinToString("") { it.messageBody.orEmpty() }

        // 1) الگوی فارسی
        val persian = Regex(
            """کد\s*فعالسازی\s*شما\s*\D*?(\d{4,8})""",
            RegexOption.MULTILINE
        ).find(fullMessage)?.groupValues?.getOrNull(1)

        if (!persian.isNullOrBlank()) return persian

        // 2) فallback
        val loose = Regex("""\b(\d{4,8})\b""").find(fullMessage)?.groupValues?.getOrNull(1)
        return loose
    }
}
