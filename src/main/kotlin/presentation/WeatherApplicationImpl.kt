package presentation

import di.appModule
import domain.contract.application.WeatherApplication
import domain.contract.constant.Constants
import domain.model.ResultWrapper
import domain.model.WeatherInfo
import domain.contract.repository.HistoryRepository
import domain.contract.repository.WeatherRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class WeatherApplicationImpl private constructor(): WeatherApplication, KoinComponent {

    private val weatherRepository: WeatherRepository by inject()
    private val historyRepository: HistoryRepository by inject()
    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.EnterCommand)
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow

    init {
        startKoin { modules(appModule) }
        _uiStateFlow.update { UiState.EnterCommand }
    }
    companion object {
        @Volatile private var instance: WeatherApplicationImpl? = null

        fun getInstance(): WeatherApplicationImpl {
            return instance ?: synchronized(this) {
                instance ?: WeatherApplicationImpl().also { instance = it }
            }
        }
    }



    override fun executeCommand(command: String, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            val commandSegments = command.split(" ")

            if (commandSegments.size in 1..2) {
                when(commandSegments[0]){
                    Constants.Application.COMMAND_TYPE_GET_WEATHER -> {
                        _uiStateFlow.update { (UiState.IsLoading) }

                        val getWeatherResult: ResultWrapper<WeatherInfo, Throwable>

                        if (commandSegments.size == 1) {
                            getWeatherResult = weatherRepository.getWeatherByIp()

                        }
                        else if (commandSegments[1].split(",").size == 2) {
                            val coordinates = commandSegments[1].split(",")
                            getWeatherResult = weatherRepository.getWeatherByLatLong(coordinates[0].toFloat(), coordinates[1].toFloat())
                        }
                        else {
                            getWeatherResult = weatherRepository.getWeatherByCity(commandSegments[1])
                        }

                        when(getWeatherResult){
                            is ResultWrapper.Success -> {
                                _uiStateFlow.update { (UiState.SuccessGetWeather(getWeatherResult.resultValue)) }
                                when(val saveSearchResult = historyRepository.saveSearch(getWeatherResult.resultValue)){
                                    is ResultWrapper.Success -> {}
                                    is ResultWrapper.CancelOrFailure -> {
                                        _uiStateFlow.update { (UiState.Error(saveSearchResult.resultValue.localizedMessage)) }
                                    }
                                }
                            }
                            is ResultWrapper.CancelOrFailure -> {
                                _uiStateFlow.update { (UiState.Error(getWeatherResult.resultValue.localizedMessage)) }
                            }
                        }
                    }

                    Constants.Application.COMMAND_TYPE_HISTORY -> {
                        _uiStateFlow.update { (UiState.IsLoading) }

                        when(val searchHistoryResult = historyRepository.getSearchHistory()){
                            is ResultWrapper.Success -> {
                                _uiStateFlow.update { (UiState.SuccessSearchHistory(searchHistoryResult.resultValue)) }
                            }
                            is ResultWrapper.CancelOrFailure -> {
                                _uiStateFlow.update { (UiState.Error(searchHistoryResult.resultValue.localizedMessage)) }
                            }
                        }
                    }

                    else -> {
                        _uiStateFlow.update { (UiState.Error(Constants.Application.RATIONAL_WRONG_COMMAND)) }
                    }
                }
            }
            else {
                _uiStateFlow.update { (UiState.Error(Constants.Application.RATIONAL_WRONG_COMMAND)) }
            }
        }.invokeOnCompletion {
            coroutineScope.launch {
                _uiStateFlow.update { (UiState.EnterCommand) }
            }
        }
    }



    @OptIn(InternalCoroutinesApi::class)
    fun printLoadingAnimation(coroutineScope: CoroutineScope): Job {
        val job = coroutineScope.launch {
            print("Loading ")
            while (true) {
                repeat(5){
                    print(".")
                    delay(200)
                }
                print("\b".repeat(5))
            }
        }

        job.invokeOnCompletion(onCancelling = true, invokeImmediately = true) {
            println()
        }

        return job
    }

}