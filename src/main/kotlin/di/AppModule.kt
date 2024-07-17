package di

import data.repository.HistoryRepositoryFileImpl
import data.repository.WeatherRepositoryKtorImpl
import data.source.local.FileLocalDataSource
import data.source.remote.KtorRemoteDataSource
import domain.repository.HistoryRepository
import domain.repository.WeatherRepository
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient{
            install(ContentNegotiation){
                gson()
            }
        }
    }
    single { KtorRemoteDataSource(get()) }
    single { FileLocalDataSource() }
    single<WeatherRepository> { WeatherRepositoryKtorImpl(get()) }
    single<HistoryRepository> { HistoryRepositoryFileImpl(get()) }
}