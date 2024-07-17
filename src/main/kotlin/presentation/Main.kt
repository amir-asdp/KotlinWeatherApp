package org.example.presentation

import kotlinx.coroutines.runBlocking
import presentation.WeatherApplicationImpl


fun main() = runBlocking {

    val app = WeatherApplicationImpl.getInstance()

    val input = readln()

    app.executeCommand(input)
}

