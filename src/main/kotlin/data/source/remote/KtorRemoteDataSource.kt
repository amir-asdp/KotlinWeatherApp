package data.source.remote

import data.model.IpResponse
import data.model.WeatherResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class KtorRemoteDataSource(private val client: HttpClient) {

    suspend fun getCurrentWeather(query: String): WeatherResponse {
        return client.get("https://api.weatherapi.com/v1/current.json") {
            url {
                parameters.append("key", "c75e8d36089c46dcaba91026241507")
                parameters.append("q", query)
            }
        }.body()
    }

    suspend fun getGlobalIp(): IpResponse {
        return client.get("https://api.ipify.org") {
            url {
                parameters.append("format", "json")
            }
        }.body()
    }

}