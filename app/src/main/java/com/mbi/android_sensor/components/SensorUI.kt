package com.mbi.android_sensor.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mbi.android_sensor.R
import com.mbi.android_sensor.sensor.Data
import com.mbi.android_sensor.sensor.InnerSensor
import com.mbi.android_sensor.sensor.NewSensor
import com.mbi.android_sensor.ui.theme.md_theme_dark_shadow


@Composable
fun SensorUI() {
    val sensorViewModel = NewSensor()
    SensorUILayout(modifier = Modifier, sensorViewModel)
}


@Composable
fun SensorUILayout(modifier: Modifier, sensor: InnerSensor) {
    Scaffold(
        topBar = {
            SensorUITitle(modifier)
        },
    ) {
        it ->
        Column(
            modifier = Modifier.padding(it)
        ) {
            SensorUIContent(modifier, sensor)
        }
    }
}

@Composable
fun SensorUITitle(modifier: Modifier) {
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.title),
            modifier = modifier.padding(dimensionResource(R.dimen.padding_large)),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
fun SensorUIContent(modifier: Modifier, sensor: InnerSensor) {
    val status = sensor.getStatus().collectAsState()
    var maxTemp by remember { mutableIntStateOf(status.value.maxInitialTemp) }
    var minTemp by remember { mutableIntStateOf(status.value.minInitialTemp) }
    var setMax by remember { mutableStateOf(false) }
    var setMin by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .fillMaxWidth().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ){
        Text(
            text = stringResource(R.string.max_temp),
            style = MaterialTheme.typography.labelLarge
        )

        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (setMax) {
                TextField(
                    value = maxTemp.toString(),
                    singleLine = true,
                    modifier = Modifier.focusTarget().border(0.dp, MaterialTheme.colorScheme.secondary),
                    onValueChange = {
                        val temp = it.toIntOrNull()
                        if (temp != null) {
                            maxTemp = temp.coerceIn(status.value.minLimit,status.value.maxLimit)
                        }else {
                            maxTemp = status.value.minInitialTemp
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    textStyle = MaterialTheme.typography.labelLarge
                )

            }else {
                Text(
                    text = "$maxTemp °C",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Button(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_small)),
                onClick = { setMax = !setMax }
            ) {
                Text(
                    text = stringResource(R.string.set_temp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Text(
            text = stringResource(R.string.min_temp),
            style = MaterialTheme.typography.labelLarge
        )

        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (setMin) {
                TextField(
                    value = minTemp.toString(),
                    singleLine = true,
                    modifier = Modifier.focusTarget().border(0.dp, MaterialTheme.colorScheme.secondary),
                    onValueChange = {
                        val temp = it.toIntOrNull()
                        if (temp != null) {
                            minTemp = temp.coerceIn(status.value.minLimit,status.value.maxLimit)
                        }else {
                            minTemp = status.value.minInitialTemp
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = MaterialTheme.typography.labelLarge
                )
            }else {
                Text(
                    text = "$minTemp °C",
                    style = MaterialTheme.typography.bodyLarge
                )

            }

            Button(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_small)),
                onClick = { setMin = !setMin }
            ) {
                Text(
                    text = stringResource(R.string.set_temp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }

        Button(
            modifier = modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .fillMaxWidth(),
            onClick = {
                sensor.updateMaxInitialTemp(maxTemp)
                sensor.updateMinInitialTemp(minTemp)
            }
        ) {

            Text(
                text = stringResource(R.string.start_sensor),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        SensorCurrentTemp(modifier, status)
        SensorLed(modifier,status)
    }

}

@Composable
fun SensorCurrentTemp(modifier: Modifier = Modifier, status: State<Data>) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_large))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.current_temp),
            style = MaterialTheme.typography.titleLarge
        )

        Box(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        ){
            Text(
                text = "${status.value.randomTemp} °C",
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

@Composable
fun SensorLed(modifier: Modifier = Modifier, status:State<Data>) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_large))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        if (status.value.statusAlert) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Warning,
                contentDescription = "Error",
                modifier = modifier.height(100.dp).width(100.dp),
                tint = Color.Red
            )
        }else {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.CheckCircle,
                contentDescription = "Check",
                modifier = modifier.height(100.dp).width(100.dp),
                tint = Color.Green
            )
        }
    }
}