package uk.co.weightwars.domain

import jakarta.inject.Inject
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class ConsecutiveDaysUseCase @Inject constructor() {
    operator fun invoke(date: LocalDate, daysCount: Int) : List<ConsecutiveDay> {
        val days = mutableListOf<ConsecutiveDay>()

        for (x in 0..daysCount - 1) {
            val newDate = date.plusDays(x.toLong())
            val dayName = newDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            days.add(
                ConsecutiveDay(
                    newDate,
                    date = newDate.toString(),
                    dayName = dayName
                )
            )
        }

        return days
    }
}

data class ConsecutiveDay(
    val localDate: LocalDate,
    val date: String,
    val dayName: String
)