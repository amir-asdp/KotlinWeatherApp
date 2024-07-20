package org.example.presentation

import domain.contract.constant.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import presentation.UiState
import presentation.WeatherApplicationImpl


fun main() = runBlocking<Unit> {

    val app = WeatherApplicationImpl.getInstance()

    var loadingAnimationJob: Lazy<Job> = lazy { app.printLoadingAnimation(this) }

    launch{
        app.uiStateFlow.collect { state ->
            when (state) {
                is UiState.EnterCommand -> {
                    loadingAnimationJob.value.cancel()
                    println(Constants.Application.ENTER_COMMAND)
                    app.executeCommand(readln().trim(), this)
                }

                is UiState.IsLoading -> {
                    loadingAnimationJob = lazy { app.printLoadingAnimation(this) }
                    loadingAnimationJob.value.start()
                }

                is UiState.SuccessGetWeather -> {
                    loadingAnimationJob.value.cancel()
                    println(
                        Constants.Application.RESULT_WEATHER_COMMAND.format(
                            state.data.requestTime, state.data.location, state.data.condition
                        )
                    )
                }

                is UiState.SuccessSearchHistory -> {
                    loadingAnimationJob.value.cancel()
                    repeat(state.data.size) { i ->
                        val weatherInfo = state.data[i]
                        println(
                            Constants.Application.RESULT_HISTORY_COMMAND.format(
                                weatherInfo.requestTime, weatherInfo.location, weatherInfo.condition
                            )
                        )
                    }
                }

                is UiState.Error -> {
                    loadingAnimationJob.value.cancel()
                    println(state.message)
                }
            }
        }
    }

}

