package com.mbi.android_sensor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable

import androidx.compose.ui.tooling.preview.Preview
import com.mbi.android_sensor.components.SensorUI
import com.mbi.android_sensor.ui.theme.SensorTheme

class MainActivity : ComponentActivity() {
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


// Dark theme
@Preview
@Composable
fun SensorUIDarkPreview() {
    SensorTheme (darkTheme = true) {
        SensorUI()
    }
}

// Light theme
@Preview
@Composable
fun SensorUILightPreview() {
    SensorTheme (darkTheme = false) {
        SensorUI()
    }
}