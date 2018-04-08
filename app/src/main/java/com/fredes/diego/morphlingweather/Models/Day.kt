package com.fredes.diego.morphlingweather.Models

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Diego on 01-04-2018.
 */
class Day(val time:Long, val minTemp:Double,val maxTemp:Double) {
    fun getFormattedTime():String{
        val formatter = SimpleDateFormat("EEEE", Locale.US)
        val date = Date(time*1000)
        val dayOfWeek = formatter.format(date)
        return dayOfWeek
    }
}