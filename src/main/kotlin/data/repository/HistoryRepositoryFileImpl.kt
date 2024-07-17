package data.repository

import data.source.local.FileLocalDataSource
import domain.model.WeatherInfo
import domain.repository.HistoryRepository

class HistoryRepositoryFileImpl(private val fileLocalDataSource: FileLocalDataSource) : HistoryRepository {

    override suspend fun saveSearch(weatherInfo: WeatherInfo) {
        try {
            val searchList = fileLocalDataSource.readJsonArrayFromFile("", "history.json")
            searchList.add(weatherInfo)
            fileLocalDataSource.saveJsonArrayToFile(searchList, "", "history.json")
        }
        catch (e: Exception) {
            println(e.message)
        }
    }

    override suspend fun getSearchHistory(): List<WeatherInfo> {
        return try {
            fileLocalDataSource.readJsonArrayFromFile("", "history.json")
        }
        catch (e: Exception) {
            println(e.message)
            mutableListOf()
        }
    }
}