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
    val total: Int,
    val scores: Set<ScoredDate>
)

data class ScoredDate(
    val localDate: LocalDate,
    val score: Int,
    val mark: ScoreMark
)  {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ScoredDate) return false
        return localDate == other.localDate
    }

    override fun hashCode(): Int {
        return localDate.hashCode()
    }

    override fun toString(): String {
        return "ScoredDate(localDate=$localDate, score=$score, mark=$mark)"
    }
}

enum class ScoreMark {
    FULL,
    HALF,
    NONE
}