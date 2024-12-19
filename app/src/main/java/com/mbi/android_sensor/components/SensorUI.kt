package com.mbi.android_sensor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.room.util.copy
import com.mbi.android_sensor.R
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
        }
    ) {
        it ->
        Column(modifier = Modifier.padding(it)) {
            SensorUIContent(modifier, sensor)
        }
    }
}

@Composable
fun SensorUITitle(modifier: Modifier) {
    Row (
        modifier = Modifier.shadow(
            elevation = dimensionResource(R.dimen.padding_small),
            shape = RectangleShape,
            ambientColor = md_theme_dark_shadow,
            spotColor = md_theme_dark_shadow
        ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.title),
            modifier = modifier.padding(dimensionResource(R.dimen.padding_large))
        )
    }
}

@Composable
fun SensorUIContent(modifier: Modifier, sensor: InnerSensor) {

}