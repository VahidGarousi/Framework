package ir.vahid.framework.contract.sms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ir.vahid.framework.contract.api.rememberBroadcastResultLauncher

@Composable
fun SmsReaderScreen(
    modifier: Modifier = Modifier,
) {
    var otp by remember { mutableStateOf("waiting...") }
    val smsLauncher = rememberBroadcastResultLauncher(
        contract = SmsReaderBroadcastReceiver(),
        singleShot = true,               // auto-unregister after the first code
        exportedOn33Plus = true,          // <- required for system SMS broadcast on API 33+
    ) { code ->
        // Got the OTP code (e.g., "123456")
        // ... proceed with verification flow
        otp = code
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = "Otp is $otp"
        )
        Button(
            onClick = {
                smsLauncher.launch(Unit)
            }
        ) {
            Text(text = "Send SMS")
        }
    }

}
