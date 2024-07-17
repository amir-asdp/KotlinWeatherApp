package presentation

import di.appModule
import domain.application.WeatherApplication
import domain.model.ResultWrapper
import domain.model.WeatherInfo
import domain.repository.HistoryRepository
import domain.repository.WeatherRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class WeatherApplicationImpl private constructor(): WeatherApplication, KoinComponent {

    private val weatherRepository: WeatherRepository by inject()
    private val historyRepository: HistoryRepository by inject()
    val _uiState = MutableStateFlow<UiState>(UiState.IsLoading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        startKoin { modules(appModule) }
    }

    companion object {
        @Volatile private var instance: WeatherApplicationImpl? = null

        fun getInstance(): WeatherApplicationImpl {
            return instance ?: synchronized(this) {
                instance ?: WeatherApplicationImpl().also { instance = it }
            }
        }
    }

    override fun executeCommand(command: String) {

        CoroutineScope(Dispatchers.Main).launch {
            uiState.collect{ state ->
                when(state){
                    is UiState.IsLoading -> {
                        printLoadingAnimation(uiState)
                    }
                    is UiState.SuccessApi -> {
                        println(state.data)
                    }
                    is UiState.SuccessFile -> {

                    }
                    is UiState.Error -> {
                        println(state.message)
                        println("----------------------------------")
                    }
                }
            }
        }


        val commandSegments = command.split(" ")

        if (commandSegments.size in 1..2) {
            when(commandSegments[0]){
                "getWeather" -> {
                    CoroutineScope(Dispatchers.IO).launch{
                        _uiState.emit(UiState.IsLoading)
                        val result: ResultWrapper<WeatherInfo, Throwable>
                        if (commandSegments.size == 1) {
                            result = weatherRepository.getWeatherByIp()

                        }
                        else if (commandSegments[1].split(",").size == 2) {
                            val latlong = commandSegments[1].split(",")
                            result = weatherRepository.getWeatherByLatLong(latlong[0].toFloat(), latlong[1].toFloat())
                        }
                        else {
                            result = weatherRepository.getWeatherByCity(commandSegments[1])
                        }
                        when(result){
                            is ResultWrapper.Success -> {
                                historyRepository.saveSearch(result.resultValue)
                                launch(Dispatchers.Main) {
                                    _uiState.emit(UiState.SuccessApi(result.resultValue))
                                }
                            }
                            is ResultWrapper.CancelOrFailure -> {
                                launch(Dispatchers.Main) {
                                    _uiState.emit(UiState.Error(result.resultValue.localizedMessage))
                                }
                            }
                        }
                    }
                }

                "history" -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        _uiState.emit(UiState.IsLoading)
                        val result = historyRepository.getSearchHistory()
                        when(result){
                            is ResultWrapper.Success -> {
                                launch(Dispatchers.Main) {
                                _uiState.emit(UiState.SuccessFile(result.resultValue))
                            }}
                            is ResultWrapper.CancelOrFailure -> {
                                launch(Dispatchers.Main) {
                                _uiState.emit(UiState.Error(result.resultValue.localizedMessage))
                            }}
                        }
                    }
                }

                else -> {
                    printWrongCommandRational()
                }
            }
        }
        else {
            printWrongCommandRational()
        }

    }

    fun printWrongCommandRational(){
        println("\nWrong Command.")
        println("Guide:")
        println("getWeather <none> or <city name> or <lat,long>   ===>   Shows current weather of a location.")
        println("history   ===>   Shows the search history.\n")
    }

    suspend fun printLoadingAnimation(uiState: StateFlow<UiState>){
        println("Loading ")
        var isLoading = false
        uiState.collect { state ->
            when(state){
                is UiState.IsLoading -> {
                    isLoading = true
                    while (isLoading){
                        for (i in 1..3){
                            print(".")
                            delay(1000)
                        }
                        print("\b".repeat(3))
                    }
                }
                is UiState.SuccessApi -> {isLoading = false}
                is UiState.SuccessFile -> {isLoading = false}
                is UiState.Error -> {isLoading = false}
            }
        }
    }

}