package com.fredes.diego.morphlingweather.API

import com.fredes.diego.morphlingweather.Models.CurrentWeather
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
}