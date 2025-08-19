package ir.vahid.framework.contract.airplane

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.vahid.framework.contract.api.rememberBroadcastResultLauncher

@Composable
fun AirplaneModeScreen(
    modifier: Modifier = Modifier,
    onChanged: (Boolean) -> Unit = {}
) {
    var isOn by remember { mutableStateOf<Boolean?>(null) }

    // Continuous listening (not single-shot) + system broadcast requires exportedOn33Plus = true
    val launcher = rememberBroadcastResultLauncher(
        contract = remember { AirplaneModeBroadcastContract() },
        singleShot = false,
        exportedOn33Plus = true
    ) { on ->
        isOn = on
        onChanged(on)
    }

    // Start listening when this Composable appears
    LaunchedEffect(Unit) { launcher.launch(Unit) }

    Column(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Airplane mode: " + when (isOn) {
            true  -> "ON ✈️"
            false -> "OFF"
            null  -> "—"
        })
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { launcher.launch(Unit) }) { Text("Start listening") }
            OutlinedButton(onClick = { launcher.cancel() }) { Text("Stop") }
        }
        Text("Tip: toggle from Quick Settings or via adb (see below).")
    }
}
