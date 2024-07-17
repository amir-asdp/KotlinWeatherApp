package presentation

import domain.model.WeatherInfo

sealed class UiState {
    data object IsLoading: UiState()
    data class SuccessApi(val data: WeatherInfo) : UiState()
    data class SuccessFile(val data: List<WeatherInfo>) : UiState()
    data class Error(val message: String) : UiState()
}