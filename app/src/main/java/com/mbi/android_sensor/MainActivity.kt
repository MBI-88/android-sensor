package com.mbi.android_sensor

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.tooling.preview.Preview
import com.mbi.android_sensor.components.SensorUI
import com.mbi.android_sensor.ui.theme.SensorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SensorTheme {
                SensorInfo()
            }
        }
    }
}


class SensorActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SensorTheme {
                SensorUI()
            }
        }
    }
}


@Composable
fun SensorInfo() {
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.info),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.author),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.description_1),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.description_2),
            style = MaterialTheme.typography.titleLarge
        )
        Button(onClick = {
            val intent = Intent(context, SensorActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(
                text = stringResource(R.string.go),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}

// Dark theme
@Preview
@Composable
fun SensorUIDarkPreview() {
    SensorTheme (darkTheme = true) {
        SensorInfo()
    }
}

// Light theme
@Preview
@Composable
fun SensorUILightPreview() {
    SensorTheme (darkTheme = false) {
        SensorInfo()
    }
}