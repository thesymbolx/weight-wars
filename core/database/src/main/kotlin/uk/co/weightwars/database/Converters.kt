package uk.co.weightwars.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class Converters {
    val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    val gson = Gson()

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
    fun fromLocalDateList(localDateList: Set<LocalDate>): String {
        val dateStrings = localDateList.map { it.format(formatter) }
        return gson.toJson(dateStrings)
    }

    @TypeConverter
    fun toLocalDateSet(jsonString: String): Set<LocalDate> {
        val listType = object : TypeToken<List<String>>() {}.type
        val dateStrings: List<String> = gson.fromJson(jsonString, listType)
        return dateStrings.map { LocalDate.parse(it, formatter) }.toSet()
    }
}