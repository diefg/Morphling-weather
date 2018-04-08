package com.fredes.diego.morphlingweather.UI

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import com.android.volley.Request
import com.fredes.diego.morphlingweather.API.API_KEY
import com.fredes.diego.morphlingweather.API.DARK_SKY_URL
import com.android.volley.toolbox.Volley
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.fredes.diego.morphlingweather.API.JSONParser
import com.fredes.diego.morphlingweather.Models.CurrentWeather
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import android.support.design.widget.Snackbar
import android.view.View
import com.fredes.diego.morphlingweather.R

class MainActivity : AppCompatActivity() {

    val TAG= MainActivity::class.java.simpleName
    val jsonParser = JSONParser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtTemp.text = getString(R.string.temp_placeholder,0)
        txtPrec.text= getString(R.string.precip_placeholder,0)
        getWeather()
    }

    private fun getWeather() {
        val latitud = "-33.4976146"//"37.8267"
        val longitud = "-70.6038334"//"-122.4233"
        val language = getString(R.string.language)
        val units = getString(R.string.units)

        val url = "$DARK_SKY_URL/$API_KEY/$latitud,$longitud?lang=$language&units=$units"

        Log.d(TAG, url)

        var queue = Volley.newRequestQueue(this)

        var stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener { response ->
                    //1 obtener el clima actual con la clase JSONParser
                    val responseJSON = JSONObject(response)
                    val currentWeather = jsonParser.getCurrentWeatherFromJson(responseJSON)
                    //2 asignar los valores a las vistas
                    buildCurrentWeatherUI(currentWeather)
                    Log.d(TAG, "la respuesta es" + response.substring(0, 500))
                }, Response.ErrorListener {

            Log.d(TAG, "error en la llamada")
            displayErrorMessage()
        })
        queue.add(stringRequest)
    }

    private fun displayErrorMessage() {
        val snackbar = Snackbar.make(main, getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), {
                    getWeather()
                })
        snackbar.show()
    }

    private fun buildCurrentWeatherUI(currentWeather: CurrentWeather) {
        val precipProbability = (currentWeather.precip.toInt()*100).toInt()
        txtTemp.text = getString(R.string.temp_placeholder,currentWeather.temp.toInt())
        txtPrec.text= getString(R.string.precip_placeholder,precipProbability)
        txtEstado.text = currentWeather.summary
        img.setImageDrawable(ResourcesCompat.getDrawable(resources,currentWeather.getIconResource(),null))
    }
    fun startHourlyActivity(view: View){
        val intent = Intent()
        intent.setClass(this,HourlyWeatherActivity::class.java)
        startActivity(intent)
    }
    fun startDailyWeather(view: View){
        val intent = Intent()
        intent.setClass(this,DailyWeatherActivity::class.java)
        startActivity(intent)
    }
}
