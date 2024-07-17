package domain.repository

import domain.model.WeatherInfo

interface HistoryRepository {

    suspend fun saveSearch(weatherInfo: WeatherInfo)
    suspend fun getSearchHistory(): List<WeatherInfo>

}