package uk.co.weightwars.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "active_challenges")
data class ActiveChallenge(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val startDate: LocalDate,
    val days: Int,
    val isHardcoreMode: Boolean,
    @Embedded val scoring: Scoring
)

data class Scoring(
    val scoredDates: Set<LocalDate>
)