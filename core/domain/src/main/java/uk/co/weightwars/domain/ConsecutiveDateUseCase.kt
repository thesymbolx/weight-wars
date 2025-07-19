package uk.co.weightwars.domain

import jakarta.inject.Inject
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class ConsecutiveDateUseCase @Inject constructor() {
    operator fun invoke(date: LocalDate, daysCount: Int) : List<ConsecutiveDate> {
        val days = mutableListOf<ConsecutiveDate>()

        for (x in 0..daysCount - 1) {
            val newDate = date.plusDays(x.toLong())
            val dayName = newDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            days.add(
                ConsecutiveDate(
                    newDate,
                    formattedDate = newDate.toString(),
                    dayName = dayName
                )
            )
        }

        return days
    }
}

data class ConsecutiveDate(
    val localDate: LocalDate,
    val formattedDate: String,
    val dayName: String
)