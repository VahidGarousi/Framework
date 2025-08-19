package ir.vahid.framework

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.vahid.core.designsystem.theme.FrameworkTheme
import ir.vahid.framework.contract.sms.new.OtpAutoReadExample
import ir.vahid.framework.contract.sms.new.OtpConsentExample
import ir.vahid.framework.contract.sms.pure.AppHashHelper
import ir.vahid.framework.contract.sms.pure.SmsReaderScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: " + AppHashHelper.getAppHashes(this))
        enableEdgeToEdge()
        setContent {
            FrameworkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OtpAutoReadExample(
                        onCode = { code ->
                            Log.d(TAG, "onCreate: $code")
                            Toast.makeText(this, "Otp is $code", Toast.LENGTH_SHORT).show()
                        },
                    )
//                    OtpConsentExample(
//                        onCode = { code ->
//                            Log.d(TAG, "onCreate: $code")
//                            Toast.makeText(this, "Otp is $code", Toast.LENGTH_SHORT).show()
//
//                        },
//                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FrameworkTheme {
        Greeting("Android")
    }
}


private const val TAG = "MainActivity"
