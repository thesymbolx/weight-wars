package uk.co.weightwars.database

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import uk.co.weightwars.database.entities.Score
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class Converters {
    val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .create()

    @TypeConverter
    fun fromString(value: String) : LocalDate = LocalDate.parse(value, formatter)

    @TypeConverter
    fun dateToString(date: LocalDate): String = date.format(formatter)

    @TypeConverter
    fun fromLocalDateList(localDateList: List<LocalDate>): String {
        val dateStrings = localDateList.map { it.format(formatter) }
        return gson.toJson(dateStrings)
    }

    @TypeConverter
    fun toLocalDateList(jsonString: String): List<LocalDate> {
        val listType = object : TypeToken<List<String>>() {}.type
        val dateStrings: List<String> = gson.fromJson(jsonString, listType)
        return dateStrings.map { LocalDate.parse(it, formatter) }
    }

    @TypeConverter
    fun fromLocalDateList(scoredDate: Set<Score>): String {
        return gson.toJson(scoredDate)
    }

    @TypeConverter
    fun toLocalDateSet(jsonString: String): Set<Score> {
        val listType = object : TypeToken<Set<Score>>() {}.type
        return gson.fromJson(jsonString, listType)
    }
}

class LocalDateAdapter : com.google.gson.JsonSerializer<LocalDate>, com.google.gson.JsonDeserializer<LocalDate> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun serialize(src: LocalDate?, typeOfSrc: java.lang.reflect.Type?, context: com.google.gson.JsonSerializationContext?): com.google.gson.JsonElement {
        return com.google.gson.JsonPrimitive(src?.format(formatter))
    }

    override fun deserialize(json: com.google.gson.JsonElement?, typeOfT: java.lang.reflect.Type?, context: com.google.gson.JsonDeserializationContext?): LocalDate {
        return LocalDate.parse(json?.asString, formatter)
    }
}