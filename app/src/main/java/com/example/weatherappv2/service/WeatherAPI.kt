package com.example.weatherappv2.service

import com.example.weatherappv2.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //////https://api.openweathermap.org/data/2.5/weather?q=London&appid=a81391b310a4e7943bbc2617454e9394
    @GET("data/2.5/weather?&units=metric&appid=a81391b310a4e7943bbc2617454e9394")
    fun getData(
        @Query("q")cityName:String
    ):Call<WeatherModel>
    //burayi list olarak dene sonradan
}