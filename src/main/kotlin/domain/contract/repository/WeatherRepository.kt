package domain.contract.repository

import domain.model.ResultWrapper
import domain.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherByIp(): ResultWrapper<WeatherInfo, Throwable>
    suspend fun getWeatherByCity(cityName: String): ResultWrapper<WeatherInfo, Throwable>
    suspend fun getWeatherByLatLong(lat: Float, long: Float): ResultWrapper<WeatherInfo, Throwable>

}