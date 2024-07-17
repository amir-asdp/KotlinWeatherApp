package domain.repository

import domain.model.ResultWrapper
import domain.model.WeatherInfo

interface HistoryRepository {

    suspend fun saveSearch(weatherInfo: WeatherInfo)
    suspend fun getSearchHistory(): ResultWrapper<List<WeatherInfo>, Throwable>

}