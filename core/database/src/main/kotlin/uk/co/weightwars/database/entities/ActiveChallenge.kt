package uk.co.weightwars.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate

data class ActiveChallenge(
    @Embedded val challengeInfo: ChallengeInfo,
    @Relation(parentColumn = "challengeInfoId", entityColumn = "challengeInfoParentId")
    val activeChallengeItems: List<ActiveChallengeItem>
)

@Entity
data class ChallengeInfo(
    @PrimaryKey(autoGenerate = true) val challengeInfoId: Long = 0,
    val title: String,
    val startDate: LocalDate,
    val days: Int,
    val isHardcoreMode: Boolean,
)

@Entity
data class ActiveChallengeItem(
    @PrimaryKey(autoGenerate = true) val activeChallengeItemId: Long = 0,
    val title: String,
    val challengeInfoParentId: Long = 0,
    val scores: Set<Score> = emptySet(),
    val lengthInDays: Int
)

data class Score(
    val localDate: LocalDate,
    val score: Int,
    val mark: ScoreMark
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Score) return false
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