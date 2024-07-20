package data.source.remote

import data.model.IpResponse
import data.model.WeatherResponse
import domain.contract.constant.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class KtorRemoteDataSource(private val client: HttpClient) {

    suspend fun getCurrentWeather(query: String): WeatherResponse {
        return client.get(Constants.Api.API_BASE_URL_CURRENT_WEATHER) {
            url {
                parameters.append("key", Constants.Api.API_KEY_WEATHER)
                parameters.append("q", query)
            }
        }.body()
    }

    suspend fun getGlobalIp(): IpResponse {
        return client.get(Constants.Api.API_BASE_URL_IPFY) {
            url {
                parameters.append("format", "json")
            }
        }.body()
    }

}