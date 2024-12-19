package com.mbi.android_sensor.sensor

import android.hardware.Sensor
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random


class SensorViewModel: InnerSensor, ViewModel()  {
    private val _uiState = MutableStateFlow(Data())
    val uiState: StateFlow<Data> = _uiState.asStateFlow()


    init {
        resetToDefault()
        randomTemp()
        signalRangeTemp()
    }


    override fun updateMaxInitialTemp(maxInitialTemp: Double) {
        _uiState.value = _uiState.value.copy(maxInitialTemp = maxInitialTemp)
    }

    override fun updateMinInitialTemp(minInitialTemp: Double) {
        _uiState.value = _uiState.value.copy(minInitialTemp = minInitialTemp)
    }

    private fun updateStatusAlert(statusAlert: Boolean) {
        _uiState.value = _uiState.value.copy(statusAlert = statusAlert)
    }

    private fun resetToDefault() {
        _uiState.value = Data()

    }

    private fun updateRandomTemp(randomTemp: Double) {
        _uiState.value = _uiState.value.copy(randomTemp = randomTemp)
    }

    private fun randomTemp(): Unit {
       CoroutineScope(Dispatchers.Default)
           .launch {
               while (true) {
                   // Generate a random number between -93 and 60
                   val random = Random.nextDouble(-93.0, 60.0)
                   updateRandomTemp(random)
                   delay(2000)
               }
           }
    }

    override val status: Data
        get() {
            return uiState.value
        }

    private fun signalRangeTemp(): Unit {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                if (uiState.value.randomTemp < uiState.value.minInitialTemp || uiState.value.randomTemp > uiState.value.maxInitialTemp ) {
                    updateStatusAlert(true)
                } else {
                    updateStatusAlert(false)
                }
                delay(1000)
            }
        }
    }
}

interface InnerSensor {
    fun updateMaxInitialTemp(maxInitialTemp: Double)
    fun updateMinInitialTemp(minInitialTemp: Double)
    val status: Data
}


fun NewSensor(): InnerSensor {
    return SensorViewModel()
}