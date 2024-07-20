package domain.contract.constant

import java.nio.file.Paths

object Constants {

    object Api {
        const val API_BASE_URL_CURRENT_WEATHER = "https://api.weatherapi.com/v1/current.json"
        const val API_BASE_URL_IPFY = "https://api.ipify.org"
        const val API_KEY_WEATHER = "c75e8d36089c46dcaba91026241507"
    }


    object File {
        val DIR_CURRENT = System.getProperty("user.dir")
        const val FILE_NAME_HISTORY = "Weather Search History.json"
    }


    object Application {
        const val COMMAND_TYPE_GET_WEATHER = "getWeather"
        const val COMMAND_TYPE_HISTORY = "history"
        const val RESULT_WEATHER_COMMAND =
            """
%s: %s current weather is %s.
            """
        const val RESULT_HISTORY_COMMAND = "--> (request time: %s) (location: %s) (condition: %s)"
        const val RATIONAL_WRONG_COMMAND = """
Wrong Command.
Guide:
getWeather <none> or <city name> or <lat,long>   ===>   Shows current weather of a location.
history   ===>   Shows the search history."""
        const val ENTER_COMMAND =
            "----------------------------------------------------------------------------------" +
                    "\nEnter the command:"
    }

}