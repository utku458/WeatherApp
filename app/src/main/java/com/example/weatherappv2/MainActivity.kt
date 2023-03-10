package com.example.weatherappv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.weatherappv2.databinding.ActivityMainBinding
import com.example.weatherappv2.model.WeatherModel
import com.example.weatherappv2.service.WeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var cityName : String
    private val BASE_URL="https://api.openweathermap.org/"
  private lateinit var weatherModel : WeatherModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.statustxt.visibility= View.GONE


        
//////https://api.openweathermap.org/data/2.5/weather?q=London&appid=a81391b310a4e7943bbc2617454e9394




        binding.imageView2.setOnClickListener {

            cityName = binding.citytxt.text.toString()

            loadData(cityName)
        }

    }

    fun loadData(cityname:String){


        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherAPI::class.java)
        val call = service.getData(cityName)

        call.enqueue(object  :Callback<WeatherModel>{
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {

                if (response.isSuccessful){
                    response.body()?.let {
                        binding.countryCodetxt.setText(it.sys.country.toString())
                        binding.cityNametxt.setText(it.name.toString())
                        binding.temperaturetxt.setText(it.main.temp.toString())

                        Glide.with(this@MainActivity).load("https://openweathermap.org/img/wn/"+ it.weather.get(0).icon + "@2x.png")
                            .into(binding.imgWeatherIcon)

                        binding.statustxt.visibility= View.VISIBLE
                        binding.statustxt.setText(it.weather.get(0).description)
                        binding.maxtemperaturetxt.setText(it.main.tempMax.toString())
                        binding.mintemperaturetxt.setText(it.main.tempMin.toString())
                        binding.feelsliketxt.setText(it.main.feelsLike.toString())
                        binding.nemtxt.setText(it.main.humidity.toString())


                    }

                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}