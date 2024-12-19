package com.mbi.android_sensor.sensor


data class Data(
    val maxInitialTemp:Double = 35.0, // Temperature maximum initial
    val minInitialTemp:Double = 5.0, // Temperature minimum initial
    val randomTemp:Double = 0.0, // Temperature random
    val statusAlert:Boolean = false, // Status alert
)
