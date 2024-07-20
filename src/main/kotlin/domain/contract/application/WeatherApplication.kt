package domain.contract.application

import kotlinx.coroutines.CoroutineScope

interface WeatherApplication {

    fun executeCommand(command: String, coroutineScope: CoroutineScope)

}