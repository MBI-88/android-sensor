package com.mbi.android_sensor.sensor

import android.hardware.Sensor
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random


class SensorViewModel(data:Data, testState:Boolean = false): InnerSensor, ViewModel()  {
    private val _uiState = MutableStateFlow(data)
    val uiState: StateFlow<Data> = _uiState.asStateFlow()
    private var testStatus:Boolean = testState


    init {
        if (!testStatus) {
            randomTemp()
            signalRangeTemp()
        }else {
            updateRandomTemp(uiState.value.randomTemp)
            checkAlert()
        }
    }


    override fun updateMaxInitialTemp(maxInitialTemp: Int) {
        _uiState.value = _uiState.value.copy(maxInitialTemp = maxInitialTemp)
    }

    override fun updateMinInitialTemp(minInitialTemp: Int) {
        _uiState.value = _uiState.value.copy(minInitialTemp = minInitialTemp)
    }

    private fun updateStatusAlert(statusAlert: Boolean) {
        _uiState.value = _uiState.value.copy(statusAlert = statusAlert)
    }

    private fun updateRandomTemp(randomTemp: Int) {
        _uiState.value = _uiState.value.copy(randomTemp = randomTemp)
    }

    private fun randomTemp() {
       CoroutineScope(Dispatchers.Default)
           .launch {
               while (true) {
                   // Generate a random number between -93 and 60
                   updateRandomTemp(Random.nextInt(uiState.value.minLimit, uiState.value.maxLimit))
                   delay(5000)
               }
           }
    }

    override fun getStatus(): StateFlow<Data> {
        return uiState
    }

    private fun signalRangeTemp() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                checkAlert()
                delay(100)
            }
        }
    }

    private fun checkAlert() {
        if (uiState.value.randomTemp < uiState.value.minInitialTemp || uiState.value.randomTemp > uiState.value.maxInitialTemp) {
            updateStatusAlert(true)
        } else {
            updateStatusAlert(false)
        }
    }
}

interface InnerSensor {
    fun updateMaxInitialTemp(maxInitialTemp: Int)
    fun updateMinInitialTemp(minInitialTemp: Int)
    fun getStatus(): StateFlow<Data>
}


fun NewSensor(data: Data = Data(maxInitialTemp = 30, minInitialTemp = 5, randomTemp = 0), testState:Boolean = false): InnerSensor {
    return SensorViewModel(data, testState)
}