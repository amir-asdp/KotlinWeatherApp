package presentation

import di.appModule
import domain.application.WeatherApplication
import domain.repository.HistoryRepository
import domain.repository.WeatherRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class WeatherApplicationImpl private constructor(): WeatherApplication, KoinComponent {

    private val weatherRepository: WeatherRepository by inject()
    private val historyRepository: HistoryRepository by inject()

    init {
        startKoin { modules(appModule) }
    }

    companion object {
        @Volatile private var instance: WeatherApplicationImpl? = null

        fun getInstance(parameter: String): WeatherApplicationImpl {
            return instance ?: synchronized(this) {
                instance ?: WeatherApplicationImpl().also { instance = it }
            }
        }
    }

    override fun executeCommand(command: String) {
        TODO("Not yet implemented")
    }

}