package ir.vahid.framework.contract.sms.new

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import ir.vahid.framework.contract.api.rememberBroadcastResultLauncher

@Composable
fun OtpConsentExample(
    onCode: (String) -> Unit,
) {
    val context = LocalContext.current

    // Launch the consent intent returned by the broadcast:
    val consentLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { res ->
        val data = res.data
        if (res.resultCode == android.app.Activity.RESULT_OK && data != null) {
            val message = data.getStringExtra(com.google.android.gms.auth.api.phone.SmsRetriever.EXTRA_SMS_MESSAGE)
            message?.let { msg ->
                OtpExtractor.from(msg)?.let(onCode)
            }
        }
    }

    val consentRx = rememberBroadcastResultLauncher(
        contract = SmsUserConsentBroadcastContract(),
        singleShot = false,                 // may emit Request then (later) Timeout
        exportedOn33Plus = true,            // system broadcast
        onResult = { event ->
            when (event) {
                is SmsConsentEvent.Request ->
                    consentLauncher.launch(event.consentIntent)

                is SmsConsentEvent.Timeout -> {
                    // optional: show retry UI / start again
                }
            }
        },
    )

    DisposableEffect(Unit) {
        context.startSmsUserConsent(sender = null) // listen for any sender
        consentRx.launch(Unit)
        onDispose { }
    }
}
