package data.repository

import data.source.local.FileLocalDataSource
import domain.contract.constant.Constants
import domain.model.ResultWrapper
import domain.model.WeatherInfo
import domain.contract.repository.HistoryRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class HistoryRepositoryFileImpl(private val fileLocalDataSource: FileLocalDataSource,
                                private val coroutineContext: CoroutineContext) : HistoryRepository {

    override suspend fun saveSearch(weatherInfo: WeatherInfo): ResultWrapper<Any, Throwable> {
        return withContext(coroutineContext) {
            try {
                val searchList = fileLocalDataSource.readJsonArrayFromFile(
                    Constants.File.DIR_CURRENT, Constants.File.FILE_NAME_HISTORY
                )
                searchList.add(weatherInfo)
                fileLocalDataSource.saveJsonArrayToFile(
                    searchList, Constants.File.DIR_CURRENT, Constants.File.FILE_NAME_HISTORY
                )
                ResultWrapper.Success(Any())
            }
            catch (t: Throwable) {
                ResultWrapper.CancelOrFailure("$t", t)
            }
        }
    }

    override suspend fun getSearchHistory(): ResultWrapper<List<WeatherInfo>, Throwable> {
        return withContext(coroutineContext) {
            try {
                ResultWrapper.Success(
                    fileLocalDataSource.readJsonArrayFromFile(Constants.File.DIR_CURRENT, Constants.File.FILE_NAME_HISTORY)
                )
            }
            catch (t: Throwable) {
                ResultWrapper.CancelOrFailure("$t", t)
            }
        }
    }

}