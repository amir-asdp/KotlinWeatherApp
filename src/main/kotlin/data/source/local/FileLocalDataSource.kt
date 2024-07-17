package data.source.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import domain.model.WeatherInfo
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class FileLocalDataSource {

    private val lock = Any()

    fun readJsonArrayFromFile(directory: String, fileName: String): MutableList<WeatherInfo> {
        synchronized(lock){
            val file = File(directory, fileName)

            return if (file.exists()) {
                FileReader(file).use { reader ->
                    val itemType = object : TypeToken<MutableList<WeatherInfo>>() {}.type
                    Gson().fromJson(reader, itemType)
                }
            } else {
                mutableListOf()
            }
        }
    }

    fun saveJsonArrayToFile(array: List<WeatherInfo>, directory: String, fileName: String) {
        synchronized(lock){
            if (!File(directory).exists()) {
                File(directory).mkdirs()
            }
            val file = File(directory, fileName)

            FileWriter(file).use { writer ->
                Gson().toJson(array, writer)
            }
        }
    }

}