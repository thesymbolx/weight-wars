package uk.co.weightwars.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate

data class ActiveChallengeEntity(
    @Embedded val challengeInfoEntity: ChallengeInfoEntity,
    @Relation(parentColumn = "challengeInfoId", entityColumn = "challengeInfoParentId")
    val subChallenges: List<SubChallengeEntity>,
    @Relation(parentColumn = "challengeInfoId", entityColumn = "challengeInfoParentId")
    val participants: List<ParticipantEntity>

)

@Entity
data class ParticipantEntity(
    @PrimaryKey(autoGenerate = true) val participantId: Long = 0,
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
data class SubChallengeEntity(
    @PrimaryKey(autoGenerate = true) val subChallengeId: Long = 0,
    val title: String,
    val challengeInfoParentId: Long = 0,
    val scores: List<ScoreEntity>,
    val lengthInDays: Int
)

data class ScoreEntity(
    val localDate: LocalDate,
    val score: Int,
    val mark: String
)