package data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

	@field:SerializedName("current")
	val current: Current? = null,

	@field:SerializedName("location")
	val location: Location? = null
)

data class Condition(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("text")
	val text: String? = null
)

data class Location(

	@field:SerializedName("localtime")
	val localtime: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("localtime_epoch")
	val localtimeEpoch: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("region")
	val region: String? = null,

	@field:SerializedName("lat")
	val lat: Double? = null,

	@field:SerializedName("tz_id")
	val tzId: String? = null
)

data class Current(

	@field:SerializedName("feelslike_c")
	val feelslikeC: Double? = null,

	@field:SerializedName("feelslike_f")
	val feelslikeF: Double? = null,

	@field:SerializedName("wind_degree")
	val windDegree: Int? = null,

	@field:SerializedName("windchill_f")
	val windchillF: Double? = null,

	@field:SerializedName("windchill_c")
	val windchillC: Double? = null,

	@field:SerializedName("last_updated_epoch")
	val lastUpdatedEpoch: Int? = null,

	@field:SerializedName("temp_c")
	val tempC: Double? = null,

	@field:SerializedName("temp_f")
	val tempF: Double? = null,

	@field:SerializedName("cloud")
	val cloud: Int? = null,

	@field:SerializedName("wind_kph")
	val windKph: Double? = null,

	@field:SerializedName("wind_mph")
	val windMph: Double? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("dewpoint_f")
	val dewpointF: Double? = null,

	@field:SerializedName("uv")
	val uv: Double? = null,

	@field:SerializedName("last_updated")
	val lastUpdated: String? = null,

	@field:SerializedName("heatindex_f")
	val heatindexF: Double? = null,

	@field:SerializedName("dewpoint_c")
	val dewpointC: Double? = null,

	@field:SerializedName("is_day")
	val isDay: Int? = null,

	@field:SerializedName("precip_in")
	val precipIn: Double? = null,

	@field:SerializedName("heatindex_c")
	val heatindexC: Double? = null,

	@field:SerializedName("wind_dir")
	val windDir: String? = null,

	@field:SerializedName("gust_mph")
	val gustMph: Double? = null,

	@field:SerializedName("pressure_in")
	val pressureIn: Double? = null,

	@field:SerializedName("gust_kph")
	val gustKph: Double? = null,

	@field:SerializedName("precip_mm")
	val precipMm: Double? = null,

	@field:SerializedName("condition")
	val condition: Condition? = null,

	@field:SerializedName("vis_km")
	val visKm: Double? = null,

	@field:SerializedName("pressure_mb")
	val pressureMb: Double? = null,

	@field:SerializedName("vis_miles")
	val visMiles: Double? = null
)
