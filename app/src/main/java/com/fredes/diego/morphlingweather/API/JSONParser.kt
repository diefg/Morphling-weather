package com.fredes.diego.morphlingweather.API

import com.fredes.diego.morphlingweather.Models.CurrentWeather
import com.fredes.diego.morphlingweather.Models.Day
import org.json.JSONObject

/**
 * Created by Diego on 05-03-2018.
 */
class JSONParser {
    fun getCurrentWeatherFromJson(response: JSONObject): CurrentWeather {
        val currentJSON = response.getJSONObject(currently)
        with(currentJSON){
            val currentWeather = CurrentWeather(getString(icon),
                                                getString(summary),
                                                getDouble(temperature),
                                                getDouble(precipProbability))
            return currentWeather
        }
    }

    fun getDailyWeather(response: JSONObject):ArrayList<Day>{
        val dailyJSON = response.getJSONObject(daily)
        val dayJSONArray = dailyJSON.getJSONArray(data)
        val days = ArrayList<Day>()

        for (i in 0..dayJSONArray.length()){
            val dayJSONObject = dayJSONArray.getJSONObject(i)
            val minTemp = dayJSONObject.getDouble(temperatureMax)
            val maxTemp = dayJSONObject.getDouble(temperatureMax)
            val time = dayJSONObject.getLong(time)
            days.add(Day(time,minTemp,maxTemp))
        }


        return days
    }

}