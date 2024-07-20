package domain.contract.repository

import domain.model.ResultWrapper
import domain.model.WeatherInfo

interface HistoryRepository {

    suspend fun saveSearch(weatherInfo: WeatherInfo): ResultWrapper<Any, Throwable>
    suspend fun getSearchHistory(): ResultWrapper<List<WeatherInfo>, Throwable>

}