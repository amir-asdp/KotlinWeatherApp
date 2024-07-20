package presentation

import domain.model.WeatherInfo

sealed class UiState {
    data object EnterCommand: UiState()
    data object IsLoading: UiState()
    data class SuccessGetWeather(val data: WeatherInfo) : UiState()
    data class SuccessSearchHistory(val data: List<WeatherInfo>) : UiState()
    data class Error(val message: String) : UiState()
}