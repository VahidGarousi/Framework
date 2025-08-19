package ir.vahid.framework.contract.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.vahid.framework.contract.api.rememberBroadcastResultLauncher

@Composable
fun ScreenOnOffScreen(
    modifier: Modifier = Modifier,
    onEvent: (ScreenEvent) -> Unit = {}
) {
    var label by remember { mutableStateOf("—") }

    val launcher = rememberBroadcastResultLauncher(
        contract = remember { ScreenBroadcastContract() },
        singleShot = false,              // keep listening
        exportedOn33Plus = true          // system broadcast on API 33+
    ) { evt ->
        label = when (evt) {
            ScreenEvent.On  -> "SCREEN ON"
            ScreenEvent.Off -> "SCREEN OFF"
        }
        onEvent(evt)
    }

    LaunchedEffect(Unit) { launcher.launch(Unit) }

    Column(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Screen state: $label")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { launcher.launch(Unit) }) { Text("Start listening") }
            OutlinedButton(onClick = { launcher.cancel() }) { Text("Stop") }
        }
    }
}
