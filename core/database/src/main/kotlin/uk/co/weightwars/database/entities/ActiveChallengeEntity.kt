package uk.co.weightwars.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate

data class ActiveChallengeEntity(
    @Embedded val challengeInfoEntity: ChallengeInfoEntity,
    @Relation(parentColumn = "challengeInfoId", entityColumn = "challengeInfoParentId")
    val activeChallengeItemEntities: List<ActiveChallengeItemEntity>
)

@Entity
data class ActiveChallengeParticipantEntity(
    val participantId: Long,
    val challengeInfoParentId: Long = 0
)

@Entity
data class ChallengeInfoEntity(
    @PrimaryKey(autoGenerate = true) val challengeInfoId: Long = 0,
    val title: String,
    val startDate: LocalDate,
    val days: Int,
    val isHardcoreMode: Boolean,
)

@Entity
data class ActiveChallengeItemEntity(
    @PrimaryKey(autoGenerate = true) val activeChallengeItemId: Long = 0,
    val title: String,
    val challengeInfoParentId: Long = 0,
    val scoreEntities: Set<ScoreEntity> = emptySet(),
    val lengthInDays: Int
)

data class ScoreEntity(
    val localDate: LocalDate,
    val score: Int,
    val mark: ScoreMark
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ScoreEntity) return false
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