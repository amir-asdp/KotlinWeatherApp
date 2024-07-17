package data.repository

import data.source.remote.KtorRemoteDataSource
import domain.model.ResultWrapper
import domain.model.WeatherInfo
import domain.repository.WeatherRepository

class WeatherRepositoryKtorImpl(private val remoteDataSource: KtorRemoteDataSource) : WeatherRepository {

    override suspend fun getWeatherByIp(): ResultWrapper<WeatherInfo, Throwable> {
        return try {
            val weatherResponse = remoteDataSource.getCurrentWeather(remoteDataSource.getGlobalIp().ip!!)
            ResultWrapper.Success(
                WeatherInfo(
                    weatherResponse.location?.name,
                    weatherResponse.current?.condition?.text,
                    weatherResponse.location?.localtime
                )
            )
        }
        catch (t: Throwable) {
            ResultWrapper.CancelOrFailure(t.localizedMessage, t)
        }
    }

    override suspend fun getWeatherByCity(cityName: String): ResultWrapper<WeatherInfo, Throwable> {
        return try {
            val weatherResponse = remoteDataSource.getCurrentWeather(cityName)
            ResultWrapper.Success(
                WeatherInfo(
                    weatherResponse.location?.name,
                    weatherResponse.current?.condition?.text,
                    weatherResponse.location?.localtime
                )
            )
        }
        catch (t: Throwable){
            ResultWrapper.CancelOrFailure(t.localizedMessage, t)
        }
    }

    override suspend fun getWeatherByLatLong(lat: Float, long: Float): ResultWrapper<WeatherInfo, Throwable> {
        return try {
            val weatherResponse = remoteDataSource.getCurrentWeather("${lat},${long}")
            ResultWrapper.Success(
                WeatherInfo(
                    weatherResponse.location?.name,
                    weatherResponse.current?.condition?.text,
                    weatherResponse.location?.localtime
                )
            )
        }
        catch (t: Throwable){
            ResultWrapper.CancelOrFailure(t.localizedMessage, t)
        }
    }
}