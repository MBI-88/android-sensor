package com.mbi.android_sensor

import com.mbi.android_sensor.sensor.Data
import com.mbi.android_sensor.sensor.NewSensor
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */



class SensorTest {
    @Test
    fun checking_right_temp() {
        val sensor = NewSensor(Data(maxInitialTemp = 35, minInitialTemp = 10, randomTemp = 20),true)
        val status = sensor.getStatus()
        assertFalse(status.value.statusAlert)
    }

    @Test
    fun checking_wrong_temp() {
        val sensor = NewSensor(Data(maxInitialTemp = 35, minInitialTemp = 25, randomTemp = 20), true)
        val status = sensor.getStatus()
        println(status.value.maxInitialTemp)
        assertTrue(status.value.statusAlert)
    }
}