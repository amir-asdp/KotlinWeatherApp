package di

import data.repository.HistoryRepositoryFileImpl
import data.repository.WeatherRepositoryKtorImpl
import data.source.local.FileLocalDataSource
import data.source.remote.KtorRemoteDataSource
import domain.contract.repository.HistoryRepository
import domain.contract.repository.WeatherRepository
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val appModule = module {
    single {
        HttpClient{
            install(ContentNegotiation){
                gson()
            }
            install(HttpRequestRetry) {
                retryOnExceptionOrServerErrors(maxRetries = 5)
                exponentialDelay()
            }
        }
    }
    single { KtorRemoteDataSource(get()) }
    single { FileLocalDataSource() }
    single<CoroutineContext> { Dispatchers.IO }
    single<WeatherRepository> { WeatherRepositoryKtorImpl(get(), get()) }
    single<HistoryRepository> { HistoryRepositoryFileImpl(get(), get()) }
}