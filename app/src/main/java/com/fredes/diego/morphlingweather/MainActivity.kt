package com.fredes.diego.morphlingweather

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import com.android.volley.Request
import com.fredes.diego.morphlingweather.API.API_KEY
import com.fredes.diego.morphlingweather.API.DARK_SKY_URL
import com.android.volley.toolbox.Volley
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.fredes.diego.morphlingweather.API.JSONParser
import com.fredes.diego.morphlingweather.API.icon
import com.fredes.diego.morphlingweather.Models.CurrentWeather
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    val TAG= MainActivity::class.java.simpleName
    val jsonParser = JSONParser()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val latitud = "37.8267"
        val longitud = "-122.4233"
        val url = "$DARK_SKY_URL/$API_KEY/$latitud,$longitud?lang=es&units=si"

       //Log.d(TAG,url)


    var queue = Volley.newRequestQueue(this)

    var stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                //1 obtener el clima actual con la clase JSONParser
                val responseJSON = JSONObject(response)
                val currentWeather = jsonParser.getCurrentWeatherFromJson(responseJSON)
                //2 asignar los valores a las vistas
                buildCurrentWeatherUI(currentWeather)

                Log.d(TAG,"la respuesta es" + response.substring(0, 500))
            }, Response.ErrorListener {
                Log.d(TAG,"error en la llamada")
            })


    queue.add(stringRequest)
    }

    private fun buildCurrentWeatherUI(currentWeather: CurrentWeather) {
        txtTemp.text = "${currentWeather.temp.toString()}C°"
        txtPrec.text="${currentWeather.precip.toString()}%"
        txtEstado.text = currentWeather.summary
        img.setImageDrawable(ResourcesCompat.getDrawable(resources,currentWeather.getIconResource(),null))
    }
}
