package com.mbi.android_sensor.sensor


data class Data(
    var maxInitialTemp:Int, // Temperature maximum initial
    var minInitialTemp:Int, // Temperature minimum initial
    var randomTemp:Int, // Temperature random
    val maxLimit:Int = 60, // Temperature maximum limit
    val minLimit:Int = -93, // Temperature minimum limit
    var statusAlert:Boolean = false, // Status alert
)
